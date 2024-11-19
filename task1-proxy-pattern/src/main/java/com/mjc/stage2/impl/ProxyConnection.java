package com.mjc.stage2.impl;


import com.mjc.stage2.Connection;

public class ProxyConnection implements Connection {
    private RealConnection realConnection;
    private ConnectionPool connectionPool;

    public ProxyConnection(RealConnection realConnection) {
        this.realConnection = realConnection;
    }

    public void reallyClose() {
        if (!realConnection.isClosed()) {
            realConnection.close(); // Close the real connection
        }
    }

    @Override
    public void close() {
        if (!realConnection.isClosed()) {
            if (connectionPool==null) connectionPool = ConnectionPool.getInstance();
            connectionPool.releaseConnection(this); // Return connection to the pool
        }
    }

    @Override
    public boolean isClosed() {
        return realConnection.isClosed(); // Delegate to RealConnection
    }
}
