package ru.job4j.io;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class EchoServer {
    public static void main(String[] args) throws IOException {
        try (ServerSocket server = new ServerSocket(9000)) {
            while (!server.isClosed()) {
                Socket socket = server.accept();
                try (OutputStream out = socket.getOutputStream();
                     BufferedReader in = new BufferedReader(
                             new InputStreamReader(socket.getInputStream()))) {
                    out.write("HTTP/1.1 200 OK\r\n\r\n".getBytes());
                    boolean hasReplied = false;
                    while (in.ready()) {
                        String str = in.readLine();
                        if (!hasReplied) {
                            String response = prepareResponse(str);
                            if ("Exit".equals(response)) {
                                server.close();
                            } else {
                                out.write(prepareResponse(str).getBytes());
                            }
                            hasReplied = true;
                        }
                        System.out.println(str);
                    }
                    out.flush();
                }
            }
        }
    }

    private static String prepareResponse(String str) {
        int indexStart = str.indexOf("?");
        int indexEnd = str.lastIndexOf(" ");
        String[] entryStr = str.substring(indexStart + 1, indexEnd).split("=");
        if (entryStr.length > 1) {
            String key = entryStr[0];
            String value = entryStr[1];
            if ("msg".equals(key)) {
                if ("Exit".equals(value)) {
                    return "Exit";
                } else if ("Hello".equals(value)) {
                    return "Hello";
                }
                return "What";
            }
            return "Enter key parameter \"msg\"";
        }
        return "Enter parameter key \"msg\" and value";
    }
}