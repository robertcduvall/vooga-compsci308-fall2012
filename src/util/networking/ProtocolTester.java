package util.networking;

public class ProtocolTester {

    /**
     * @param args
     */
    public static void main (String[] args) {
        ProtocolXMPP xmpp = new ProtocolXMPP();
        System.out.println(xmpp.openStream("server"));
        System.out.println(xmpp.sendMessage("Connor Gordon", "Oren Bukspan", "goduke."));
    }

}
