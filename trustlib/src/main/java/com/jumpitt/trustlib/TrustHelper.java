package com.jumpitt.trustlib;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;
import android.telephony.gsm.GsmCellLocation;
import android.util.Log;

import com.jumpitt.trustlib.model.Device;
import com.jumpitt.trustlib.model.SIM;
import com.jumpitt.trustlib.network.RestClient;
import com.jumpitt.trustlib.network.TrifleBody;
import com.jumpitt.trustlib.network.TrifleResponse;
import com.jumpitt.trustlib.utils.Permissions;
import com.jumpitt.trustlib.utils.TrustListener;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.NetworkInterface;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.jumpitt.trustlib.utils.Permissions.REQUEST_PERMISSION;

public class TrustHelper extends AppCompatActivity {
    final private static String MEM_FILE = "/proc/meminfo";
    final private static String CPU_FILE = "/proc/cpuinfo";

    private static final String[] permissionNeeded = new String[]{Manifest.permission.READ_PHONE_STATE, Manifest.permission.ACCESS_COARSE_LOCATION};

    private Permissions mPermissions;
    private TrifleBody mBody;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mPermissions = new Permissions(this, permissionNeeded);
    }

    /**
     * Este método revisa si se han aceptado los permisos necesarios
     */
    public void getTrifles() {
        if (mPermissions.areGranted()) {
            getData();
        } else {
            mPermissions.shouldShowRequestPermissionRationale();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_PERMISSION: {
                if (grantResults.length > 0) {
                    for (int grant : grantResults) {
                        if (grant != PackageManager.PERMISSION_GRANTED) {
                            mPermissions.shouldShowRequestPermissionRationale();
                            return;
                        }
                    }
                    getData();
                } else {
                    mPermissions.shouldShowRequestPermissionRationale();
                }
                break;
            }
        }
    }

    /**
     * Este método inicia la obtención de información y posterior envío al servidor
     */
    private void getData() {
        Device device = new Device();
        getDeviceData(device);
        //getMemData(device);
        getMemDataCat(device);
        //getCPUData(device);
        getCPUDataCat(device);
        getImei(device);
        device.setWlan0Mac(getMacAddress());

        String uuid = UUID.randomUUID().toString();
        device.setUUID(uuid);

        List<SIM> simList = getTelInfo();

        mBody = new TrifleBody();
        mBody.setDevice(device);
        mBody.setSim(simList);

        sendTrifles(null);
    }

    /**
     * Envia las minucias recogidas al servidos para obtener un identificador
     *
     * @param listener envíalo si quieres recuperar la respuesta desde tu aplicación
     */
    public void sendTrifles(@Nullable final TrustListener.OnCreateTrifle listener) {
        Log.d("TRUSTLIB", "Creando trifle");
        if (mBody == null) return;

        Call<TrifleResponse> createTrifle = RestClient.get().trifle(mBody);
        createTrifle.enqueue(new Callback<TrifleResponse>() {
            @Override
            public void onResponse(Call<TrifleResponse> call, Response<TrifleResponse> response) {
                Log.d("TRUSTLIB", "Audit Code:" + response.code());
                if (listener != null) {
                    if (response.isSuccessful()) {
                        if (response.body() != null) {
                            listener.onSuccess(response.body().getAudit());
                        } else {
                            listener.onFailure(new Throwable("Cannot get the response body"));
                        }
                    } else {
                        listener.onError(response.code());
                    }
                }
            }

            @Override
            public void onFailure(Call<TrifleResponse> call, Throwable t) {
                Log.e("TRUSTLIB", "Audit Failure");
                if (listener != null)
                    listener.onFailure(t);
            }
        });
    }

    /**
     * Obtiene la mac-address del dispositvo wlan0 si está disponible
     * @return mac address del dispositivo, 02:00:00:00:00:00 si no lo encuentra o hay algun error
     */
    public static String getMacAddress() {
        try {
            List<NetworkInterface> all = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface nif : all) {
                if (!nif.getName().equalsIgnoreCase("wlan0")) continue;

                byte[] macBytes = nif.getHardwareAddress();
                if (macBytes == null) {
                    return "";
                }

                StringBuilder res1 = new StringBuilder();
                for (byte b : macBytes) {
                    //res1.append(Integer.toHexString(b & 0xFF) + ":");
                    res1.append(String.format("%02X:", b));
                }

                if (res1.length() > 0) {
                    res1.deleteCharAt(res1.length() - 1);
                }
                return res1.toString();
            }
        } catch (Exception ignore) {
        }
        return "02:00:00:00:00:00";
    }


    /**
     * Este metodo agrega al objeto recibido los valores de MemTotal y SwapTotal
     * obtenidos desde el archivo /proc/meminfo
     *
     * @param device Objeto que almacena la información obtenida desde el dispositivo
     */
    private void getMemData(Device device) {
        FileReader fstream;
        try {
            fstream = new FileReader("/proc/meminfo");
        } catch (FileNotFoundException e) {
            Log.e("readMem", "Could not read " + MEM_FILE);
            return;
        }
        BufferedReader in = new BufferedReader(fstream);
        String line;
        try {
            while ((line = in.readLine()) != null) {
                switch (getKey(line)) {
                    case "KernelStack":
                        device.setKernelStack(getValue(line).trim());
                        break;
                    case "MemTotal":
                        device.setMemTotal(getValue(line).trim());
                        break;
                    case "SwapTotal":
                        device.setSwapTotal(getValue(line).trim());
                        break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();

        }
    }

    /**
     * Este metodo agrega al objeto recibido los valores de MemTotal, SwapTotal y KernelStack
     * obtenidos desde el archivo /proc/meminfo mediando comandos de systema
     *
     * @param device Objeto que almacena la información obtenida desde el dispositivo
     */
    private void getMemDataCat(Device device) {
        try {
            Process P = Runtime.getRuntime().exec("cat " + MEM_FILE);
            P.waitFor();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(P.getInputStream()));

            String line;
            while ((line = bufferedReader.readLine()) != null) {
                switch (getKey(line)) {
                    case "KernelStack":
                        device.setKernelStack(getValue(line).trim());
                        break;
                    case "MemTotal":
                        device.setMemTotal(getValue(line).trim());
                        break;
                    case "SwapTotal":
                        device.setSwapTotal(getValue(line).trim());
                        break;
                }
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Este metodo agrega al objeto recibido los valores buscados sobre la CPU
     * obtenidos desde el archivo /proc/cpuinfo mediante comandos de sistema
     *
     * @param device Objeto que almacena la información obtenida desde el dispositivo
     */
    private void getCPUDataCat(Device device) {
        try {
            Process P = Runtime.getRuntime().exec("cat " + CPU_FILE);
            P.waitFor();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(P.getInputStream()));

            int processorsCount = 0;
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                if (line.contains("processor"))
                    processorsCount++;

                if (line.contains("Processor"))
                    device.setProcessorModelName(getValue(line).trim());
                if (line.contains("model name"))
                    device.setProcessorModelName(getValue(line).trim());
                if (line.contains("BogoMIPS"))
                    device.setProcessorBogomips(getValue(line).trim());
                if (line.contains("Features"))
                    device.setProcessorFeatures(getValue(line).trim());
                if (line.contains("Hardware"))
                    device.setProcessorHardware(getValue(line).trim());
                if (line.contains("Revision"))
                    device.setProcessorRevision(getValue(line).trim());
                if (line.contains("Serial"))
                    device.setProcessorSerial(getValue(line).trim());
                if (line.contains("Device"))
                    device.setProcessorDevice(getValue(line).trim());
                if (line.contains("Radio"))
                    device.setProcessorRadio(getValue(line).trim());
                if (line.contains("MSM Hardware"))
                    device.setProcessorMsmHardware(getValue(line).trim());
                if (line.contains("CPU implementer"))
                    device.setCpuImplementer(getValue(line).trim());
                if (line.contains("CPU architecture"))
                    device.setCpuArchitecture(getValue(line).trim());
                if (line.contains("CPU variant"))
                    device.setCpuVariant(getValue(line).trim());
                if (line.contains("CPU part"))
                    device.setCpuPart(getValue(line).trim());
                if (line.contains("CPU revision"))
                    device.setCpuRevision(getValue(line).trim());
            }

            device.setProcessorQuantity(String.valueOf(processorsCount));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Este metodo agrega al objeto recibido los valores buscados sobre la CPU
     * obtenidos desde el archivo /proc/cpuinfo
     *
     * @param device Objeto que almacena la información obtenida desde el dispositivo
     */
    private void getCPUData(Device device) {
        FileReader fstream;
        try {
            fstream = new FileReader(CPU_FILE);
        } catch (FileNotFoundException e) {
            Log.e("readMem", "Could not read " + CPU_FILE);
            device.setMemTotal(null);
            device.setSwapTotal(null);
            return;
        }
        int processorsCount = 0;
        BufferedReader in = new BufferedReader(fstream, 500);
        String line;
        try {
            while ((line = in.readLine()) != null) {
                if (line.contains("processor"))
                    processorsCount++;

                if (line.contains("Processor"))
                    device.setProcessorModelName(getValue(line));
                if (line.contains("model name"))
                    device.setProcessorModelName(getValue(line));
                if (line.contains("BogoMIPS"))
                    device.setProcessorBogomips(getValue(line));
                if (line.contains("Features"))
                    device.setProcessorFeatures(getValue(line));
                if (line.contains("Hardware"))
                    device.setProcessorHardware(getValue(line));
                if (line.contains("Revision"))
                    device.setProcessorRevision(getValue(line));
                if (line.contains("Serial"))
                    device.setProcessorSerial(getValue(line));
                if (line.contains("Device"))
                    device.setProcessorDevice(getValue(line));
                if (line.contains("Radio"))
                    device.setProcessorRadio(getValue(line));
                if (line.contains("MSM Hardware"))
                    device.setProcessorMsmHardware(getValue(line));
                if (line.contains("CPU implementer"))
                    device.setCpuImplementer(getValue(line));
                if (line.contains("CPU architecture"))
                    device.setCpuArchitecture(getValue(line));
                if (line.contains("CPU variant"))
                    device.setCpuVariant(getValue(line));
                if (line.contains("CPU part"))
                    device.setCpuPart(getValue(line));
                if (line.contains("CPU revision"))
                    device.setCpuRevision(getValue(line));

            }

            device.setProcessorQuantity(String.valueOf(processorsCount));
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Este método extrae información de un string del tipo "key: value type"
     *
     * @param line linea con información a extraer
     * @return el valor extraido de la linea
     */
    private String getValue(String line) {
        String[] segs = line.trim().split(":");
        if (segs.length >= 2) {
            return segs[1];
        }
        return null;
    }

    /**
     * Este método extrae información de un string del tipo "key: value type"
     *
     * @param line linea con información a extraer
     * @return la key extraida de la linea
     */
    private String getKey(String line) {
        String[] segs = line.trim().split(":");
        if (segs.length >= 2) {
            return segs[0];
        }
        return null;
    }

    /**
     * Agrega toda la información relevante obtenida desde Build
     *
     * @param device Objeto que almacena la información obtenida desde el dispositivo
     */
    @SuppressLint("MissingPermission")
    private void getDeviceData(Device device) {
        device.setBoard(Build.BOARD);
        device.setBrand(Build.BRAND);
        device.setDisplay(Build.DISPLAY);
        device.setDevice(Build.DEVICE);
        device.setFingerprint(Build.FINGERPRINT);
        device.setHardware(Build.HARDWARE);
        device.setId(Build.ID);
        device.setHost(Build.HOST);
        device.setManufacturer(Build.MANUFACTURER);
        device.setModel(Build.MODEL);
        device.setProduct(Build.PRODUCT);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            device.setSerial(Build.getSerial());
        } else {
            device.setSerial(Build.SERIAL);
        }
    }

    public void getSim1IMSI() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
            String imsi = null;
            TelephonyManager tm = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
            try {
                Method getSubId = TelephonyManager.class.getMethod("getSubscriberId", int.class);
                SubscriptionManager sm = (SubscriptionManager) getSystemService(TELEPHONY_SUBSCRIPTION_SERVICE);

                imsi = (String) getSubId.invoke(tm, sm.getActiveSubscriptionInfoForSimSlotIndex(0).getSubscriptionId()); // Sim slot 1 IMSI

            } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Este método agrega el imei del dispositivo y la version del software
     *
     * @param device Objeto que almacena la información obtenida desde el dispositivo
     */
    @SuppressLint({"MissingPermission", "HardwareIds"})
    private void getImei(Device device) {
        TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);

        if (tm != null) {
            device.setImei(tm.getDeviceId());
            device.setSoftwareVersion(tm.getDeviceSoftwareVersion());
        }
    }

    /**
     * Este método obtiene la info de cada sim disponible
     * <p>
     * Casos Particulares Encontrados:
     * Objetos de prueba: Chip entel - Chip claro
     * Test 1: Chip claro primer slot -> OK
     * Test 2: Chip claro segundo slot -> Devuelve unos pocos datos
     * Test 3: Chip claro slot 1 Chip entel slot 2 -> OK
     * Test 4: Chip entel slot 1 Chip claro slot 2 -> Datos duplicados
     * Test 5-6: Chip entel en cualquier slot -> OK
     * En todas las pruebas, el segundo slot devuelve un string de ceros "000000000000" para el MEID
     *
     * @return lista de datos obtenidos de cada SIM
     */
    private List<SIM> getTelInfo() {
        List<SIM> sims = new ArrayList<>();

        TelephonyManager telephonyManager = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
        //Por defecto asumiremos que tiene 2 slots
        //Se comprueba antes si esta disponible la SIM en el slot consultado asi que no hay riesgo de errors

        int simCount = 2;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (telephonyManager != null)
                //Desde API 22 se puede obtener el número de SIMs disponibles
                simCount = telephonyManager.getPhoneCount();
        }

        //Iteramos para ver si está disponible la información en cada sim
        for (int i = 0; i < simCount; i++) {
            SIM sim = getSimDataAtSlot(i);
            if (sim != null) sims.add(sim);
        }

        return sims;
    }

    @SuppressLint("MissingPermission")
    private SIM getSimDataAtSlot(int slot) {
        SIM sim = null;
        try {
            TelephonyManager telephonyManager = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);

            if (telephonyManager != null) {
                if (getSIMStateBySlot(slot)) {
                    sim = new SIM();
                    sim.setIccid(getDeviceIdBySlot("getSimSerialNumber", slot));
                    sim.setImsi(getDeviceIdBySlot("getSubscriberId", slot));
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        sim.setMeidEsn(telephonyManager.getMeid(slot));
                        sim.setImei(telephonyManager.getImei(slot));
                    } else {
                        sim.setImei(getDeviceIdBySlot("getDeviceId", slot));
                    }
                    sim.setSpn(getDeviceIdBySlot("getSimOperatorName", slot));
                    sim.setMcc(getDeviceIdBySlot("getNetworkCountryIso", slot));
                    sim.setMnc(getDeviceIdBySlot("getNetworkOperatorName", slot));
                    sim.setMccmnc(getDeviceIdBySlot("getSimOperator", slot));
                    sim.setMsisdn(getDeviceIdBySlot("getLine1Number", slot)); //Actualmente celulares con android O pueden retornar este valor

                    //Los valores de LAC y CID solo se pueden obtener desde el primer slot
                    if (telephonyManager.getPhoneType() == TelephonyManager.PHONE_TYPE_GSM) {
                        final GsmCellLocation location = (GsmCellLocation) telephonyManager.getCellLocation();
                        if (location != null) {
                            sim.setLac(Integer.toString(location.getLac()));
                            sim.setCid(Integer.toString(location.getCid()));
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return sim;
    }


    /**
     * Obtiene el estado de la sim en el slot indicado
     *
     * @param slotID número del slot que se desea consultar
     * @return true si la sim se encuentra disponible en el slot indicado
     */
    private boolean getSIMStateBySlot(int slotID) {
        boolean isReady = false;
        try {
            String simStateString = getDeviceIdBySlot("getSimState", slotID);
            if (simStateString != null) {
                int simState = Integer.parseInt(simStateString);
                if ((simState != TelephonyManager.SIM_STATE_ABSENT) && (simState != TelephonyManager.SIM_STATE_UNKNOWN)) {
                    isReady = true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return isReady;
    }

    /**
     * Obtiene el valor del methodo consultado
     *
     * @param predictedMethodName nombre del método a consultar
     * @param slotID              número del slot que se desea consultar
     * @return true si la sim se encuentra disponible en el slot indicado
     */
    private String getDeviceIdBySlot(String predictedMethodName, int slotID) {
        String result = null;
        TelephonyManager telephony = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        try {
            Class<?> telephonyClass = Class.forName(telephony.getClass().getName());
            Class<?>[] parameter = new Class[1];
            parameter[0] = int.class;
            Method getSimID = telephonyClass.getMethod(predictedMethodName, parameter);

            Object[] obParameter = new Object[1];
            obParameter[0] = slotID;
            Object ob_phone = getSimID.invoke(telephony, obParameter);

            if (ob_phone != null) {
                result = ob_phone.toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }


    //Unused
    //pero interesante
    /*
    private static String simState(int simState) {
        switch (simState) {
            case 0:
                return "UNKNOWN";
            case 1:
                return "ABSENT";
            case 2:
                return "REQUIRED";
            case 3:
                return "PUK_REQUIRED";
            case 4:
                return "NETWORK_LOCKED";
            case 5:
                return "READY";
            case 6:
                return "NOT_READY";
            case 7:
                return "PERM_DISABLED";
            case 8:
                return "CARD_IO_ERROR";
        }
        return "??? " + simState;
    }
    */
}
