package core;

/**
 * @author ashish
 */
/*
* POJO class created as a bridge between the input/output JSON files
* and the GSON Handler
* */
public class UserInfo {
private Long click_id;
private String created_at;
private Long product_id;
private Long user_id;
private String ip;
private String country;
private String status;
private Double product_price;

    public UserInfo(Long click_id, String created_at, Long product_id, Long user_id, String ip, String country, String status, Double product_price) {
        this.click_id = click_id;
        this.created_at = created_at;
        this.product_id = product_id;
        this.user_id = user_id;
        this.ip = ip;
        this.country = country;
        this.status = status;
        this.product_price = product_price;
    }

    @Override
    public String toString() {
        return "UserInfo{" +
                "click_id=" + click_id +
                ", created_at='" + created_at + '\'' +
                ", product_id=" + product_id +
                ", user_id=" + user_id +
                ", ip='" + ip + '\'' +
                ", country='" + country + '\'' +
                ", status='" + status + '\'' +
                ", product_price=" + product_price +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserInfo userInfo = (UserInfo) o;

        return product_id != null ? product_id.equals(userInfo.product_id) : userInfo.product_id == null;
    }

    @Override
    public int hashCode() {
        return product_id != null ? product_id.hashCode() : 0;
    }

    public Double getProduct_price() {
        return product_price;
    }

    public void setProduct_price(Double product_price) {
        this.product_price = product_price;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getClick_id() {
        return click_id;
    }

    public void setClick_id(Long click_id) {
        this.click_id = click_id;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public Long getProduct_id() {
        return product_id;
    }

    public void setProduct_id(Long product_id) {
        this.product_id = product_id;
    }

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }
}
