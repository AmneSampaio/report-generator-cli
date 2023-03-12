package org.company;

class Phone {

    private Integer countryCode;
    private boolean status;
    private String number;
    private String type;

    public Phone(Integer countryCode, boolean status, String number, String type) {
        this.countryCode = countryCode;
        this.status = status;
        this.number = number;
        this.type = type;
    }

    public Integer getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(Integer countryCode) {
        this.countryCode = countryCode;
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Phone)) return false;

        Phone phone = (Phone) o;

        if (status != phone.status) return false;
        if (!countryCode.equals(phone.countryCode)) return false;
        if (!number.equals(phone.number)) return false;
        return type.equals(phone.type);
    }

    @Override
    public int hashCode() {
        int result = countryCode.hashCode();
        result = 31 * result + (status ? 1 : 0);
        result = 31 * result + number.hashCode();
        result = 31 * result + type.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Phone{" +
                "countryCode=" + countryCode +
                ", status=" + status +
                ", number='" + number + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
