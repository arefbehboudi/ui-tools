package com.ui.tools;

import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.Location;
import org.flywaydb.core.api.MigrationInfo;
import org.flywaydb.core.api.configuration.Configuration;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Service
public class FlywayService {

    private final Flyway flyway;

    public FlywayService(Flyway flyway) {
        this.flyway = flyway;
    }

    public Map<String, Object> getFlywayData() {
        Map<String, Object> data = new HashMap<>();

        try {
            Configuration configuration = flyway.getConfiguration();
            MigrationInfo[] migrationInfos = flyway.info().all();

            data.put("datasource", configuration.getDataSource().getConnection().getMetaData().getURL());
            data.put("encoding", configuration.getEncoding());
            data.put("migrationInfo", migrationInfos);
            data.put("initOnMigrate", configuration.getInitSql());
            data.put("outOfOrder", configuration.isOutOfOrder());
            data.put("isCleanOnValidationError", configuration.isCleanOnValidationError());
            data.put("table", configuration.getTable());
            data.put("baselineDescription", configuration.getBaselineDescription());
            data.put("sqlMigrationPrefix", configuration.getSqlMigrationPrefix());
            data.put("locations", Arrays.stream(configuration.getLocations()).map(Location::getDescriptor).toArray());
            data.put("schemas", configuration.getSchemas());

        } catch (Exception e) {

        }


        return data;
    }
}
