package shared;

import java.io.Serializable;

public class NodeInfo implements Serializable {
    private final String ip;
    private final int port;
    private final int no;

    public NodeInfo(String ip, int port, int no) {
        this.ip = ip;
        this.port = port;
        this.no = no;
    }

    public String getIp() {
        return ip;
    }

    public int getPort() {
        return port;
    }

    public int getNo() {
        return no;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        NodeInfo nodeInfo = (NodeInfo) o;

        if (port != nodeInfo.port) return false;
        if (no != nodeInfo.no) return false;
        return ip != null ? ip.equals(nodeInfo.ip) : nodeInfo.ip == null;
    }

    @Override
    public int hashCode() {
        int result = ip != null ? ip.hashCode() : 0;
        result = 31 * result + port;
        result = 31 * result + no;
        return result;
    }

    @Override
    public String toString() {
        String type = String.valueOf(port).startsWith("6") ? "Br" : "Pub";
        return type + no + "{" + ip + ':' + port + '}';
    }
}
