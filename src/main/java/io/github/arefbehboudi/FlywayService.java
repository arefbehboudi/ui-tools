package io.github.arefbehboudi;

import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.FlywayException;
import org.flywaydb.core.api.Location;
import org.flywaydb.core.api.MigrationInfo;
import org.flywaydb.core.api.configuration.Configuration;
import org.flywaydb.core.api.output.MigrateResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.*;

@Service
public class FlywayService {

    @Autowired(required = false)
    private Flyway flyway;

    public Map<String, Object> getFlywayData() {
        Map<String, Object> data = new HashMap<>();

        try {
            Configuration configuration = flyway.getConfiguration();

            List<Map<String, Object>> migrationInfos = getMigrationInfos();

            try {
                data.put("datasource", configuration.getDataSource().getConnection().getMetaData().getURL());
            } catch (SQLException e) {
                e.printStackTrace();
            }
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
            e.printStackTrace();
        }


        return data;
    }

    private List<Map<String, Object>> getMigrationInfos() {
        MigrationInfo[] migrationInfos = flyway.info().all();

        List<Map<String, Object>> migrations = new ArrayList<>();

        for (MigrationInfo migrationInfo : migrationInfos) {
            Map<String, Object> migration = new HashMap<>();
            migration.put("installedOn", migrationInfo.getChecksum());
            migration.put("rawVersion", migrationInfo.getVersion().getRawVersion());
            migration.put("description", migrationInfo.getDescription());
            migration.put("type", migrationInfo.getType().name());
            migration.put("script", migrationInfo.getScript());
            migration.put("state", migrationInfo.getState().getDisplayName());
            migration.put("executionTime", migrationInfo.getExecutionTime());
            migration.put("checksum", migrationInfo.getChecksum());

            migrations.add(migration);
        }

        migrations.sort(Comparator.comparing(o -> Double.parseDouble(o.get("rawVersion").toString())));

        return migrations;
    }

    public Map<String, Object> migrate() {
        Map<String, Object> result = new HashMap<>();
        try {
            MigrateResult migrateResult = flyway.migrate();

            result.put("success", true);
            result.put("totalMigrationTime", migrateResult.getTotalMigrationTime());
            result.put("operation", migrateResult.getOperation());
        } catch (FlywayException e) {
            result.put("success", false);
            result.put("errorCode", e.getErrorCode().name());
            result.put("exception", e.getMessage());
        }catch (Exception e) {
            result.put("success", false);
            result.put("errorCode", HttpStatus.INTERNAL_SERVER_ERROR);
            result.put("exception", e.getMessage());
        }
        return result;

    }

    public Map<String, Object> undo() {
        Map<String, Object> result = new HashMap<>();
        try {
            flyway.undo();

            result.put("success", true);
        } catch (FlywayException e) {
            result.put("success", false);
            result.put("errorCode", e.getErrorCode().name());
            result.put("exception", e.getMessage());
        }catch (Exception e) {
            result.put("success", false);
            result.put("errorCode", HttpStatus.INTERNAL_SERVER_ERROR);
            result.put("exception", e.getMessage());
        }
        return result;

    }

    public Map<String, Object> repair() {
        Map<String, Object> result = new HashMap<>();
        try {
            flyway.repair();

            result.put("success", true);
        } catch (FlywayException e) {
            result.put("success", false);
            result.put("errorCode", e.getErrorCode().name());
            result.put("exception", e.getMessage());
        }catch (Exception e) {
            result.put("success", false);
            result.put("errorCode", HttpStatus.INTERNAL_SERVER_ERROR);
            result.put("exception", e.getMessage());
        }
        return result;

    }

    public Map<String, Object> validate() {
        Map<String, Object> result = new HashMap<>();
        try {
            flyway.validate();

            result.put("success", true);
        } catch (FlywayException e) {
            result.put("success", false);
            result.put("errorCode", e.getErrorCode().name());
            result.put("exception", e.getMessage());
        }catch (Exception e) {
            result.put("success", false);
            result.put("errorCode", HttpStatus.INTERNAL_SERVER_ERROR);
            result.put("exception", e.getMessage());
        }
        return result;

    }

    public Map<String, Object> clean() {
        Map<String, Object> result = new HashMap<>();
        try {
            flyway.clean();

            result.put("success", true);
        } catch (FlywayException e) {
            result.put("success", false);
            result.put("errorCode", e.getErrorCode().name());
            result.put("exception", e.getMessage());
        }catch (Exception e) {
            result.put("success", false);
            result.put("errorCode", HttpStatus.INTERNAL_SERVER_ERROR);
            result.put("exception", e.getMessage());
        }
        return result;

    }
}
