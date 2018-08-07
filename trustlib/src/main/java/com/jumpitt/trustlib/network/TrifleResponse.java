package com.jumpitt.trustlib.network;

import com.google.gson.annotations.SerializedName;

public class TrifleResponse {
    private Audit audit;

    public Audit getAudit() {
        return audit;
    }

    public class Audit {
        @SerializedName("trust_id")
        private String trustId;
        @SerializedName("_id")
        private ID id;
        @SerializedName("created_at")
        private String createdAt;
        @SerializedName("updated_at")
        String updatedAt;

        public String getTrustId() {
            return trustId;
        }

        public ID getId() {
            return id;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public String getUpdatedAt() {
            return updatedAt;
        }

        public class ID {
            @SerializedName("$oid")
            private String id;

            public String getId() {
                return id;
            }
        }
    }
}
