package com.jeeplus.modules.business.entity;

import org.springframework.web.util.HtmlUtils;

import com.jeeplus.common.persistence.BusinessDataEntity;

public class Venues  extends BusinessDataEntity<Venues>{
	private static final long serialVersionUID = 1L;

	private Integer id;

    private String venueName; 

    private String contact;

    private String phone;

    private String email;

    private Byte type;

    private String bigImg;

    private String smallImg;

    private Short capacity;

    private String provinceCode;

    private String cityCode;

    private String city;

    private String address;

    private String latitude;

    private String longitude;

    private String venueDesc;

    private String remark;

    private String content;
    
    private String capacityScope ;  //人数容量范围，为查询使用
    private Short capacityStart ;  //人数容量
    private Short capacityEnd ;  //人数容量
    

    
    public Short getCapacityStart() {
		return capacityStart;
	}

	public void setCapacityStart(Short capacityStart) {
		this.capacityStart = capacityStart;
	}

	public Short getCapacityEnd() {
		return capacityEnd;
	}

	public void setCapacityEnd(Short capacityEnd) {
		this.capacityEnd = capacityEnd;
	}

	public String getCapacityScope() {
		return capacityScope;
	}

	public void setCapacityScope(String capacityScope) {
		this.capacityScope = capacityScope;
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getVenueName() {
        return venueName;
    }

    public void setVenueName(String venueName) {
        this.venueName = venueName;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
    }

    public String getBigImg() {
        return bigImg;
    }

    public void setBigImg(String bigImg) {
        this.bigImg = bigImg;
    }

    public String getSmallImg() {
        return smallImg;
    }

    public void setSmallImg(String smallImg) {
        this.smallImg = smallImg;
    }

    public Short getCapacity() {
        return capacity;
    }

    public void setCapacity(Short capacity) {
        this.capacity = capacity;
    }

    public String getProvinceCode() {
        return provinceCode;
    }

    public void setProvinceCode(String provinceCode) {
        this.provinceCode = provinceCode;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getVenueDesc() {
        return venueDesc;
    }

    public void setVenueDesc(String venueDesc) {
        this.venueDesc = venueDesc;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getContent() {
        return HtmlUtils.htmlUnescape(content);
    }

    public void setContent(String content) {
        this.content = content;
    }
}