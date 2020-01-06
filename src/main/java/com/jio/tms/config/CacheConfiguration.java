package com.jio.tms.config;

import java.time.Duration;

import org.ehcache.config.builders.*;
import org.ehcache.jsr107.Eh107Configuration;

import org.hibernate.cache.jcache.ConfigSettings;
import io.github.jhipster.config.JHipsterProperties;

import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.*;

@Configuration
@EnableCaching
public class CacheConfiguration {

    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        JHipsterProperties.Cache.Ehcache ehcache = jHipsterProperties.getCache().getEhcache();

        jcacheConfiguration = Eh107Configuration.fromEhcacheCacheConfiguration(
            CacheConfigurationBuilder.newCacheConfigurationBuilder(Object.class, Object.class,
                ResourcePoolsBuilder.heap(ehcache.getMaxEntries()))
                .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofSeconds(ehcache.getTimeToLiveSeconds())))
                .build());
    }

    @Bean
    public HibernatePropertiesCustomizer hibernatePropertiesCustomizer(javax.cache.CacheManager cacheManager) {
        return hibernateProperties -> hibernateProperties.put(ConfigSettings.CACHE_MANAGER, cacheManager);
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            createCache(cm, com.jio.tms.repository.UserRepository.USERS_BY_LOGIN_CACHE);
            createCache(cm, com.jio.tms.repository.UserRepository.USERS_BY_EMAIL_CACHE);
            createCache(cm, com.jio.tms.domain.User.class.getName());
            createCache(cm, com.jio.tms.domain.Authority.class.getName());
            createCache(cm, com.jio.tms.domain.User.class.getName() + ".authorities");
            createCache(cm, com.jio.tms.domain.CompanyProfile.class.getName());
            createCache(cm, com.jio.tms.domain.Customer.class.getName());
            createCache(cm, com.jio.tms.domain.Customer.class.getName() + ".loadOrders");
            createCache(cm, com.jio.tms.domain.Customer.class.getName() + ".invoices");
            createCache(cm, com.jio.tms.domain.Customer.class.getName() + ".morecontacts");
            createCache(cm, com.jio.tms.domain.Customer.class.getName() + ".transactionsRecords");
            createCache(cm, com.jio.tms.domain.Trip.class.getName());
            createCache(cm, com.jio.tms.domain.Trip.class.getName() + ".invoices");
            createCache(cm, com.jio.tms.domain.Invoice.class.getName());
            createCache(cm, com.jio.tms.domain.Invoice.class.getName() + ".invoiceItems");
            createCache(cm, com.jio.tms.domain.InvoiceItem.class.getName());
            createCache(cm, com.jio.tms.domain.Accounts.class.getName());
            createCache(cm, com.jio.tms.domain.Accounts.class.getName() + ".transactionsRecords");
            createCache(cm, com.jio.tms.domain.TransactionsRecord.class.getName());
            createCache(cm, com.jio.tms.domain.Equipment.class.getName());
            createCache(cm, com.jio.tms.domain.Equipment.class.getName() + ".trips");
            createCache(cm, com.jio.tms.domain.Insurance.class.getName());
            createCache(cm, com.jio.tms.domain.Contact.class.getName());
            createCache(cm, com.jio.tms.domain.Driver.class.getName());
            createCache(cm, com.jio.tms.domain.Driver.class.getName() + ".trips");
            createCache(cm, com.jio.tms.domain.OwnerOperator.class.getName());
            createCache(cm, com.jio.tms.domain.OwnerOperator.class.getName() + ".loadOrders");
            createCache(cm, com.jio.tms.domain.Location.class.getName());
            // jhipster-needle-ehcache-add-entry
        };
    }

    private void createCache(javax.cache.CacheManager cm, String cacheName) {
        javax.cache.Cache<Object, Object> cache = cm.getCache(cacheName);
        if (cache != null) {
            cm.destroyCache(cacheName);
        }
        cm.createCache(cacheName, jcacheConfiguration);
    }

}
