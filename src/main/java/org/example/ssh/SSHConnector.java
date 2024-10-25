


package org.example.ssh;

import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

import java.io.*;
import java.util.Properties;

public class SSHConnector {

    private static final JSch SECURE_CHANNEL = new JSch();

    private boolean connected;

    private final String hostname;
    private final int port;
    private final String username;
    private final String password;
    private PrintStream printStream;

    private Session session;
    private ChannelExec channel;

    public SSHConnector(String hostname, int port, String username, String password) {
        this.hostname = hostname;
        this.port = port;
        this.username = username;
        this.password = password;
        this.connected = false;
    }

    private void readConsole() throws IOException {
        if(this.session == null || this.channel == null) return;
        InputStream inputStream = this.channel.getInputStream();
        byte[] temp = new byte[1024];
        while (true) {
            while (inputStream.available() > 0) {
                int result = inputStream.read(temp, 0, 1024);
                if (result < 0) break;
                System.out.print(new String(temp, 0, result));
            }
            if (this.channel.isClosed()) {
                System.out.println("Exit-Status: " + this.channel.getExitStatus());
                break;
            }
            try {
                Thread.sleep(1000);
            } catch (Exception exception) {
                throw new RuntimeException(exception);
            }
        }
    }

    public void connect() throws JSchException, IOException {
        this.session = SECURE_CHANNEL.getSession(this.username, this.hostname, this.port);
        this.session.setPassword(this.password);
        Properties config = new Properties();
        config.put("StrictHostKeyChecking", "no");
        this.session.setConfig(config);
        this.session.connect();
        this.connected = true;
        this.readConsole();
    }

    public void disconnect() {
        if(!this.connected) return;
        this.channel.disconnect();
        this.session.disconnect();
        this.connected = false;
    }

    public String execute(String command) throws JSchException, IOException {
        if (this.channel == null) {
            this.channel = (ChannelExec) this.session.openChannel("exec");
            this.channel.setInputStream(null);
            this.channel.setErrStream(System.err);
            this.channel.setCommand(command);
        }
        if (this.printStream == null) this.printStream = new PrintStream(this.channel.getOutputStream(), true);
        this.channel.connect();
        this.printStream.println(command);
        this.printStream.flush();
        return getResult();
    }

    private String getResult() throws IOException {
        if (this.session == null || this.channel == null) return null;
        InputStream inputStream = this.channel.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        return reader.readLine();
    }

    public boolean isConnected() {
        return connected;
    }

}

