package netty;

/**
 * @author pengtao
 * @create 2022/9/3 0016
 */
public interface Init {
    int PORT = 5050;
    String HOST = "localhost";
    String WEB_SOCKET_URL = String.format("ws://%s:%d/websocket", HOST, PORT);
}
