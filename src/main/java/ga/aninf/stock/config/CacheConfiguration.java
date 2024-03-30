package ga.aninf.stock.config;

import java.time.Duration;
import org.ehcache.config.builders.*;
import org.ehcache.jsr107.Eh107Configuration;
import org.hibernate.cache.jcache.ConfigSettings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.boot.info.BuildProperties;
import org.springframework.boot.info.GitProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.*;
import tech.jhipster.config.JHipsterProperties;
import tech.jhipster.config.cache.PrefixedKeyGenerator;

@Configuration
@EnableCaching
public class CacheConfiguration {

    private GitProperties gitProperties;
    private BuildProperties buildProperties;
    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        JHipsterProperties.Cache.Ehcache ehcache = jHipsterProperties.getCache().getEhcache();

        jcacheConfiguration = Eh107Configuration.fromEhcacheCacheConfiguration(
            CacheConfigurationBuilder.newCacheConfigurationBuilder(
                Object.class,
                Object.class,
                ResourcePoolsBuilder.heap(ehcache.getMaxEntries())
            )
                .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofSeconds(ehcache.getTimeToLiveSeconds())))
                .build()
        );
    }

    @Bean
    public HibernatePropertiesCustomizer hibernatePropertiesCustomizer(javax.cache.CacheManager cacheManager) {
        return hibernateProperties -> hibernateProperties.put(ConfigSettings.CACHE_MANAGER, cacheManager);
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            createCache(cm, ga.aninf.stock.repository.UserRepository.USERS_BY_LOGIN_CACHE);
            createCache(cm, ga.aninf.stock.repository.UserRepository.USERS_BY_EMAIL_CACHE);
            createCache(cm, ga.aninf.stock.domain.User.class.getName());
            createCache(cm, ga.aninf.stock.domain.Authority.class.getName());
            createCache(cm, ga.aninf.stock.domain.User.class.getName() + ".authorities");
            createCache(cm, ga.aninf.stock.domain.Entrepot.class.getName());
            createCache(cm, ga.aninf.stock.domain.Entrepot.class.getName() + ".mouvementStocks");
            createCache(cm, ga.aninf.stock.domain.Entrepot.class.getName() + ".stocks");
            createCache(cm, ga.aninf.stock.domain.Entrepot.class.getName() + ".inventaires");
            createCache(cm, ga.aninf.stock.domain.Structure.class.getName());
            createCache(cm, ga.aninf.stock.domain.Structure.class.getName() + ".entrepots");
            createCache(cm, ga.aninf.stock.domain.Structure.class.getName() + ".employes");
            createCache(cm, ga.aninf.stock.domain.Structure.class.getName() + ".ventes");
            createCache(cm, ga.aninf.stock.domain.Structure.class.getName() + ".abonnements");
            createCache(cm, ga.aninf.stock.domain.Produit.class.getName());
            createCache(cm, ga.aninf.stock.domain.Produit.class.getName() + ".mouvementStocks");
            createCache(cm, ga.aninf.stock.domain.Produit.class.getName() + ".stocks");
            createCache(cm, ga.aninf.stock.domain.Produit.class.getName() + ".inventaires");
            createCache(cm, ga.aninf.stock.domain.Produit.class.getName() + ".ventes");
            createCache(cm, ga.aninf.stock.domain.Categorie.class.getName());
            createCache(cm, ga.aninf.stock.domain.Categorie.class.getName() + ".produits");
            createCache(cm, ga.aninf.stock.domain.MouvementStock.class.getName());
            createCache(cm, ga.aninf.stock.domain.Stock.class.getName());
            createCache(cm, ga.aninf.stock.domain.Inventaire.class.getName());
            createCache(cm, ga.aninf.stock.domain.Vente.class.getName());
            createCache(cm, ga.aninf.stock.domain.Abonnement.class.getName());
            createCache(cm, ga.aninf.stock.domain.Paiement.class.getName());
            createCache(cm, ga.aninf.stock.domain.Paiement.class.getName() + ".abonnements");
            createCache(cm, ga.aninf.stock.domain.PlansAbonnement.class.getName());
            createCache(cm, ga.aninf.stock.domain.PlansAbonnement.class.getName() + ".abonnements");
            createCache(cm, ga.aninf.stock.domain.PlansAbonnement.class.getName() + ".paiements");
            createCache(cm, ga.aninf.stock.domain.Employe.class.getName());
            createCache(cm, ga.aninf.stock.domain.Permission.class.getName());
            createCache(cm, ga.aninf.stock.domain.Permission.class.getName() + ".authorities");
            // jhipster-needle-ehcache-add-entry
        };
    }

    private void createCache(javax.cache.CacheManager cm, String cacheName) {
        javax.cache.Cache<Object, Object> cache = cm.getCache(cacheName);
        if (cache != null) {
            cache.clear();
        } else {
            cm.createCache(cacheName, jcacheConfiguration);
        }
    }

    @Autowired(required = false)
    public void setGitProperties(GitProperties gitProperties) {
        this.gitProperties = gitProperties;
    }

    @Autowired(required = false)
    public void setBuildProperties(BuildProperties buildProperties) {
        this.buildProperties = buildProperties;
    }

    @Bean
    public KeyGenerator keyGenerator() {
        return new PrefixedKeyGenerator(this.gitProperties, this.buildProperties);
    }
}
