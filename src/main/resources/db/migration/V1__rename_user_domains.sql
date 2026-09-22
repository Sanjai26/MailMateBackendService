RENAME TABLE
    users TO clients,
    master_user TO users,
    user_attribute_definitions TO client_attribute_definitions,
    user_attribute_options TO client_attribute_options,
    scheduler_user_mapping TO scheduler_client_mapping;

ALTER TABLE scheduler_client_mapping
    RENAME COLUMN user_id TO client_id;

ALTER TABLE service_master
    RENAME COLUMN user_id TO client_id;

ALTER TABLE trail_edit
    RENAME COLUMN user_id TO client_id;

UPDATE users
SET last_updated_at = COALESCE(last_updated_at, lastUpdatedAt);

ALTER TABLE users
    DROP COLUMN lastUpdatedAt;
