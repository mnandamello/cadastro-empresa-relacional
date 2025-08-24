package com.mafe.cadastroempresarelacional.interfaces.dto.request;

public class ChangePasswordRequest {
    private String oldPassword;
    private String newPassword;

    public ChangePasswordRequest(String oldPassword, String newPassword){
        this.oldPassword = oldPassword;
        this.newPassword = newPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }
}
