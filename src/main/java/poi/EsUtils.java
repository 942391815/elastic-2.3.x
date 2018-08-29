package poi;

import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.transport.TransportAddress;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by qiaogu on 2016/8/29.
 */
public class EsUtils {
    public static TransportClient localClient() {
        //测试平台
        return localClient("my-application", "127.0.0.1", "9300");
    }

    public static TransportClient localClient(String clusterName, String host, String port) {
        //测试平台

        TransportClient transportClient = null;
        try {

            Map<String, String> map = new HashMap<String, String>();
            map.put("cluster.name", clusterName);
            Settings.Builder settings = Settings.builder().put(map);
            transportClient = TransportClient.builder().settings(settings).build()
                    .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(host), Integer.parseInt(port)));

        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        return transportClient;
    }

    public static void main(String[] args) {
        localClient();
    }
}
