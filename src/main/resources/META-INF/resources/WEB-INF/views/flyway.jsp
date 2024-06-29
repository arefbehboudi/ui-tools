<%@ page import="org.flywaydb.core.Flyway" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.sql.Connection" %>
<%@ page import="org.flywaydb.core.api.MigrationInfo" %>
<%@ page import="org.flywaydb.core.api.Location" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Flyway Dashboard</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
    <style>
        body {
            padding-top: 70px;
        }
        .table th, .table td {
            vertical-align: middle;
        }
    </style>
</head>
<body>
<nav class="navbar navbar-expand-lg navbar-light bg-light fixed-top">
    <a class="navbar-brand" href="#">
        Flyway Dashboard
    </a>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>
    <div class="collapse navbar-collapse" id="navbarNav">
        <ul class="navbar-nav ml-auto">
            <li class="nav-item">
                <a class="nav-link" href="#configuration">Configuration</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="#migration">Migration</a>
            </li>
        </ul>
    </div>
</nav>

<div class="container">
    <% Flyway flyway = (Flyway) request.getAttribute("flyway"); %>
    <% if (flyway == null) { %>
    <div class="alert alert-danger mt-5">
        <strong>Error:</strong> No Flyway instance found!
    </div>
    <p>You should:</p>
    <ul>
        <li>Define a class implementing <code>org.flywaydb.core.Flyway</code></li>
        <li>Add a services folder in META-INF with a file <code>/META-INF/services/org.flywaydb.core.Flyway</code> containing the full name of the previous class</li>
    </ul>
    <% } else { %>
    <h2 class="text-info"><a id="configuration">Configuration</a></h2>
    <table class="table table-striped table-hover">
        <tbody>
        <tr>
            <th scope="row">Datasource</th>
            <td>
                <% Connection connection = null;
                    try {
                        connection = flyway.getConfiguration().getDataSource().getConnection();
                        out.print(connection.getMetaData().getURL());
                    }catch (Exception e) {
                        System.out.println(e.getMessage());
                    } finally {
                        if (connection != null) {
                            connection.close();
                        }
                    }
                %>
            </td>
        </tr>
        <tr>
            <th scope="row">Encoding</th>
            <td><%= flyway.getConfiguration().getEncoding() %></td>
        </tr>
        <tr>
            <th scope="row">Is init on migrate?</th>
            <td>
                <% if (flyway.getConfiguration().isValidateOnMigrate()) { %>
                <span class="text-success"><i class="fas fa-check"></i></span>
                <% } else { %>
                <span class="text-danger"><i class="fas fa-times"></i></span>
                <% } %>
            </td>
        </tr>
        <tr>
            <th scope="row">Is out of order?</th>
            <td>
                <% if (flyway.getConfiguration().isOutOfOrder()) { %>
                <span class="text-success"><i class="fas fa-check"></i></span>
                <% } else { %>
                <span class="text-danger"><i class="fas fa-times"></i></span>
                <% } %>
            </td>
        </tr>
        <tr>
            <th scope="row">Is clean on validation error?</th>
            <td>
                <% if (flyway.getConfiguration().isCleanOnValidationError()) { %>
                <span class="text-success"><i class="fas fa-check"></i></span>
                <% } else { %>
                <span class="text-danger"><i class="fas fa-times"></i></span>
                <% } %>
            </td>
        </tr>
        <tr>
            <th scope="row">Is validate on migrate?</th>
            <td>
                <% if (flyway.getConfiguration().isValidateOnMigrate()) { %>
                <span class="text-success"><i class="fas fa-check"></i></span>
                <% } else { %>
                <span class="text-danger"><i class="fas fa-times"></i></span>
                <% } %>
            </td>
        </tr>
        <tr>
            <th scope="row">Table</th>
            <td><%= flyway.getConfiguration().getTable() %></td>
        </tr>
        <tr>
            <th scope="row">Init description</th>
            <td><%= flyway.getConfiguration().getBaselineDescription() %></td>
        </tr>
        <tr>
            <th scope="row">SQL Migration Prefix</th>
            <td><%= flyway.getConfiguration().getSqlMigrationPrefix() %></td>
        </tr>
        <tr>
            <th scope="row">Locations</th>
            <td>
                <% Location[] locations = flyway.getConfiguration().getLocations();
                    for (Location location : locations) {
                        out.println(location);
                    }
                %>
            </td>
        </tr>
        <tr>
            <th scope="row">Schemas</th>
            <td>
                <% String[] schemas = flyway.getConfiguration().getSchemas();
                    for (String schema : schemas) {
                        out.println(schema);
                    }
                %>
            </td>
        </tr>
        <tr>
            <th scope="row">Placeholder Prefix</th>
            <td><%= flyway.getConfiguration().getPlaceholderPrefix() %></td>
        </tr>
        <tr>
            <th scope="row">Placeholder Suffix</th>
            <td><%= flyway.getConfiguration().getPlaceholderSuffix() %></td>
        </tr>
        <tr>
            <th scope="row">Placeholders</th>
            <td>
                <% Map<String, String> placeholders = flyway.getConfiguration().getPlaceholders();
                    for (Map.Entry<String, String> placeholder : placeholders.entrySet()) {
                        out.println("key: " + placeholder.getKey() + ", value: " + placeholder.getValue());
                    }
                %>
            </td>
        </tr>
        </tbody>
    </table>

    <h2 class="text-info"><a id="migration">Migration</a></h2>
    <% MigrationInfo[] info = flyway.info().all(); %>
    <table class="table table-striped table-hover">
        <thead class="thead-dark">
        <tr>
            <th scope="col">Version</th>
            <th scope="col">Description</th>
            <th scope="col">Type</th>
            <th scope="col">Script</th>
            <th scope="col">State</th>
            <th scope="col">Date</th>
            <th scope="col">Execution Time</th>
            <th scope="col">Checksum</th>
        </tr>
        </thead>
        <tbody>
        <% for (MigrationInfo migrationInfo : info) { %>
        <tr>
            <td><%= migrationInfo.getVersion() %></td>
            <td><%= migrationInfo.getDescription() %></td>
            <td><%= migrationInfo.getType() %></td>
            <td><%= migrationInfo.getScript() %></td>
            <td><%= migrationInfo.getState().getDisplayName() %></td>
            <td><%= migrationInfo.getInstalledOn() %></td>
            <td><%= migrationInfo.getExecutionTime() %></td>
            <td><%= migrationInfo.getChecksum() %></td>
        </tr>
        <% } %>
        </tbody>
    </table>
    <% } %>
</div> <!-- /container -->

<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"></script>
<script src="https://kit.fontawesome.com/a076d05399.js"></script>
</body>
</html>
