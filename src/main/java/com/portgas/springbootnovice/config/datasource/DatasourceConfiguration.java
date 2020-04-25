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

    private final DataSourceProperties dataSourceProperties;

    @Autowired
    public DatasourceConfiguration(CurrentUser currentUser,DataSourceProperties dataSourceProperties) {
        this.currentUser = currentUser;
        this.dataSourceProperties = dataSourceProperties;
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

    private DataSource generateDataSource(DBType dbType){

        DataSource result = null;
        switch (dbType){
            case REPLICATE:
                result = this.generateDataSource(
                        dataSourceProperties.getDriverClassName()
                        ,dataSourceProperties.getUrl()
                        ,dataSourceProperties.getUsername()
                        ,dataSourceProperties.getPassword()
                );

                break;
            case MASTER:
                result = this.generateDataSource(
                        dataSourceProperties.getDriverClassName()
                        ,dataSourceProperties.getUrl()
                        ,dataSourceProperties.getUsername()
                        ,dataSourceProperties.getPassword()
                );
            default:
                break;
        }

        return result;
    }

    /**
     *
     * @param dataSourceProperties
     * @return
     */
    private DataSource generateDataSource(String... dataSourceProperties){
        DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.driverClassName(dataSourceProperties[0]);
        dataSourceBuilder.url(dataSourceProperties[1]);
        dataSourceBuilder.username(dataSourceProperties[2]);
        dataSourceBuilder.password(dataSourceProperties[3]);
        return dataSourceBuilder.build();
    }
}
