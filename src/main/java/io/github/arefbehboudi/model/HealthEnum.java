package io.github.arefbehboudi.model;

public enum HealthEnum {
    CASSANDRA("cassandra"),
    COUCHBASE("couchbase"),
    DB("db"),
    DISKSPACE("diskSpace"),
    ELASTICSEARCH("elasticsearch"),
    HAZELCAST("hazelcast"),
    JMS("jms"),
    LDAP("ldap"),
    MAIL("mail"),
    MONGO("mongo"),
    NEO_4_J("neo4j"),
    PING("ping"),
    RABBIT("rabbit"),
    REDIS("redis"),
    SSL("ssl")
    ;

    private final String health;

    HealthEnum(String health) {
        this.health = health;
    }

    public static HealthEnum getByName(String health) {
        for (HealthEnum value : HealthEnum.values()) {
            if(value.health.equals(health))
                return value;
        }
        return null;
    }
}
