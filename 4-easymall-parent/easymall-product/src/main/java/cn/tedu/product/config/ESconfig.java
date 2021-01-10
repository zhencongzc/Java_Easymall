package cn.tedu.product.config;

import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;

@Configuration
public class ESconfig {
    @Value("${easymall.elasticsearch.nodes}")
    private List<String> nodes;//10.42.168.46:9300

    @Bean
    public TransportClient initClient() throws UnknownHostException {
        TransportClient client = new PreBuiltTransportClient(Settings.EMPTY);
        for (String node : nodes) {
            String host = node.split(":")[0];
            int port = Integer.parseInt(node.split(":")[1]);
            TransportAddress address = new InetSocketTransportAddress(InetAddress.getByName(host), port);
            client.addTransportAddress(address);
        }
        return client;
    }
}
