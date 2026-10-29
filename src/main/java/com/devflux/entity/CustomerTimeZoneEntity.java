package com.devflux.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "customer_time_zones")
public class CustomerTimeZoneEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "zone_id", nullable = false, unique = true, length = 100)
    private String zoneId;

    @Column(name = "display_name", nullable = false, length = 150)
    private String displayName;

    @Column(name = "utc_offset", nullable = false, length = 6)
    private String utcOffset;

    @Column(name = "is_active", nullable = false)
    private boolean isActive = true;

    public CustomerTimeZoneEntity() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getZoneId() {
        return zoneId;
    }

    public void setZoneId(String zoneId) {
        this.zoneId = zoneId;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getUtcOffset() {
        return utcOffset;
    }

    public void setUtcOffset(String utcOffset) {
        this.utcOffset = utcOffset;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

	@Override
	public String toString()
	{
		StringBuilder builder = new StringBuilder();
		builder.append("CustomerTimeZoneEntity [id=");
		builder.append(id);
		builder.append(", zoneId=");
		builder.append(zoneId);
		builder.append(", displayName=");
		builder.append(displayName);
		builder.append(", utcOffset=");
		builder.append(utcOffset);
		builder.append(", isActive=");
		builder.append(isActive);
		builder.append("]");
		return builder.toString();
	}
}