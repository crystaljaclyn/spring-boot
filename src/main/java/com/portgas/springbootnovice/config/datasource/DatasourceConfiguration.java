package com.portgas.springbootnovice.config.datasource;

import io.ebean.EbeanServer;
import io.ebean.EbeanServerFactory;
import io.ebean.config.ServerConfig;
import io.ebean.config.dbplatform.mysql.MySqlPlatform;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@SpringBootConfiguration
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
        Map<Object,Object> targetDataSource = this.generateDataSource();
        RoutingDatasource routingDatasource = new RoutingDatasource();
        routingDatasource.setDefaultTargetDataSource( this.generateDataSource( DBType.MASTER ) );
        routingDatasource.setTargetDataSources(targetDataSource);

        return routingDatasource;
    }

    /**
     * Ebean Config datasource
     * @param dataSource
     * @return
     */
    @Bean(name = "ebeanConfig")
    public ServerConfig ebeanServerConfig(DataSource dataSource) {

        ServerConfig config = new ServerConfig();
        config.setName("db");
        config.setDatabasePlatform(new MySqlPlatform());
        config.setDefaultServer(true);
        config.setDataSource(dataSource);
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

       resultMap.put(DBType.MASTER,this.generateDataSource(DBType.MASTER));
       resultMap.put(DBType.REPLICATE,this.generateDataSource(DBType.REPLICATE));

       return resultMap;
    }

    /**
     *
     * @param dbType
     * @return
     */
    private DataSource generateDataSource(DBType dbType){
        //Data source
        DataSourceProperties dataSourceProperties = dataSourcePropertyMap.get(dbType.name().toLowerCase());

        DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
        if( dataSourceProperties != null ){
            dataSourceBuilder.driverClassName(dataSourceProperties.getDriverClassName());
            dataSourceBuilder.url(dataSourceProperties.getUrl());
            dataSourceBuilder.username(dataSourceProperties.getUsername());
            dataSourceBuilder.password(dataSourceProperties.getPassword());
        }
        return dataSourceBuilder.build();
    }
}
