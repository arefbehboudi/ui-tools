package io.github.arefbehboudi;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.core.io.Resource;
import org.springframework.web.servlet.resource.ResourceResolver;
import org.springframework.web.servlet.resource.ResourceResolverChain;
import java.util.List;

public class FlywayResourceSolver implements ResourceResolver {

    @Override
    public Resource resolveResource(HttpServletRequest request, String requestPath, List<? extends Resource> locations, ResourceResolverChain chain) {
        Resource resolved = chain.resolveResource(request, requestPath, locations);
        if (resolved == null) {
            String webJarResourcePath = "";//findWebJarResourcePath(requestPath);
            if (webJarResourcePath != null)
                return chain.resolveResource(request, webJarResourcePath, locations);
        }
        return resolved;
    }

    @Override
    public String resolveUrlPath(String resourcePath, List<? extends Resource> locations, ResourceResolverChain chain) {
        String path = chain.resolveUrlPath(resourcePath, locations);
        if (path == null) {
            String webJarResourcePath = "";//findWebJarResourcePath(resourcePath);
            if (webJarResourcePath != null)
                return chain.resolveUrlPath(webJarResourcePath, locations);
        }
        return path;
    }
}
