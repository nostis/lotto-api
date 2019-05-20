package com.nostis.lottoapi.config;

import com.nostis.lottoapi.model.Lotto;
import com.nostis.lottoapi.model.MiniLotto;
import com.nostis.lottoapi.model.MultiMulti;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.core.mapping.ExposureConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.http.HttpMethod;

@Configuration
public class RestConfig implements RepositoryRestConfigurer {
    @Override
    public void configureRepositoryRestConfiguration(RepositoryRestConfiguration restConfig) {
        ExposureConfiguration config = restConfig.getExposureConfiguration();

        config.forDomainType(Lotto.class).withItemExposure(((metadata, httpMethods) -> httpMethods.disable(HttpMethod.DELETE, HttpMethod.PATCH, HttpMethod.POST, HttpMethod.PUT)));
        config.forDomainType(MiniLotto.class).withItemExposure(((metadata, httpMethods) -> httpMethods.disable(HttpMethod.DELETE, HttpMethod.PATCH, HttpMethod.POST, HttpMethod.PUT)));
        config.forDomainType(MultiMulti.class).withItemExposure(((metadata, httpMethods) -> httpMethods.disable(HttpMethod.DELETE, HttpMethod.PATCH, HttpMethod.POST, HttpMethod.PUT)));
    }
}
