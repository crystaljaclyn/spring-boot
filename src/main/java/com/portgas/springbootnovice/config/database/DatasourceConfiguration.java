package com.portgas.springbootnovice.config.database;

import com.zaxxer.hikari.HikariDataSource;
import io.ebean.EbeanServer;
import io.ebean.EbeanServerFactory;
import io.ebean.config.ExternalTransactionManager;
import io.ebean.config.ServerConfig;
import io.ebean.config.dbplatform.mysql.MySqlPlatform;
import io.ebean.spring.txn.SpringJdbcTransactionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@SpringBootConfiguration
@EnableTransactionManagement
public class DatasourceConfiguration {

    private final CurrentUser currentUser;

    private final Map<String,DataSourceProperties> dataSourcePropertyMap;

    @Autowired
    public DatasourceConfiguration(CurrentUser currentUser,Map<String,DataSourceProperties> dataSourcePropertyMap) {
        this.currentUser = currentUser;
        this.dataSourcePropertyMap = dataSourcePropertyMap;
    }

    /**
     * Multi routing data source
     * @return
     */
    @Bean(name ="datasource")
    public DataSource dataSource(){

        HikariDataSource defaultDataSource = (HikariDataSource) this.generateDataSource( dataSourcePropertyMap.get(DBType.MASTER.name().toLowerCase()));
        defaultDataSource.setMaximumPoolSize( 2 );
        defaultDataSource.setAutoCommit(false);
        //Generate Multi datasource
        Map<Object,Object> targetDataSource = this.generateDataSource();

        //Routing datasource config
        RoutingDatasource routingDatasource = new RoutingDatasource();
        routingDatasource.setDefaultTargetDataSource( defaultDataSource );
        routingDatasource.setTargetDataSources(targetDataSource);

        return routingDatasource;
    }

    /**
     * Multi routing data source
     * @return
     */
    @Bean(name ="springTransactionManager")
    public ExternalTransactionManager externalTransactionManager(){
        return new SpringJdbcTransactionManager();
    }
    /**
     * Ebean Config datasource
     * @param dataSource
     * @return
     */
    @Bean(name = "ebeanConfig")
    public ServerConfig ebeanServerConfig(DataSource dataSource,ExternalTransactionManager springTransactionManager) {

        //Set Ebean config
        ServerConfig config = new ServerConfig();
        config.setName("db");
        config.setDatabasePlatform(new MySqlPlatform());
        config.setDefaultServer(true);
        config.setDataSource(dataSource);
        config.setExternalTransactionManager( springTransactionManager );
        config.setCurrentUserProvider(currentUser);
        return config;
    }

    /**
     * Set up Ebean Server
     * @param serverConfig
     * @return
     */
    @Bean
    public EbeanServer ebeanServer(ServerConfig serverConfig) {
        return EbeanServerFactory.create(serverConfig);
    }

    /**
     *
     * @return
     */
    private Map<Object,Object> generateDataSource(){
       Map<Object,Object> resultMap = new HashMap<>();
       for(DBType dbType : DBType.values()){
           DataSourceProperties dataSourceProperties = dataSourcePropertyMap.get(dbType.name().toLowerCase());
           HikariDataSource dataSource = (HikariDataSource) this.generateDataSource(dataSourceProperties);
           this.configDataSource( dataSource , dataSourceProperties);
           resultMap.put(dbType,dataSource);
       }

       return resultMap;
    }

    /**
     *
     * @param dataSourceProperties
     * @return
     */
    private DataSource generateDataSource(DataSourceProperties dataSourceProperties){
        DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.driverClassName(dataSourceProperties.getDriverClassName());
        dataSourceBuilder.url(dataSourceProperties.getUrl());
        dataSourceBuilder.username(dataSourceProperties.getUsername());
        dataSourceBuilder.password(dataSourceProperties.getPassword());
        return  dataSourceBuilder.build();
    }

    /**
     *
     * @param dataSource
     * @param dataSourceProperties
     */
    private void configDataSource(HikariDataSource dataSource , DataSourceProperties dataSourceProperties){
        dataSource.setMaximumPoolSize( dataSourceProperties.getMaxConnectionPool() );
        dataSource.setAutoCommit(false);
    }
}
