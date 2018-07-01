package com.wrichikbiswas.wifilock;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiManager;
import android.os.IBinder;

public class LockService extends Service {

	WifiManager.WifiLock lock;
	State state;

	public LockService() {
		super();
		state = State.STARTED;
		AppUtils.service = this;
	}

	@Override
	public IBinder onBind(Intent intent) {
		// TODO: Return the communication channel to the service.
		throw new UnsupportedOperationException("Not yet implemented");
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		AppUtils.service = this;
		return super.onStartCommand(intent, flags, startId);
	}

	void stop() {
		lock.release();
		AppUtils.service = null;
		stopSelf();
	}

	void lock() {
		state = State.LOCKED;
		if (lock == null) {
			lock = ((WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE)).createWifiLock("WifiLock TAG");
		}
		lock.acquire();
	}

	void release() {
		state = State.RELEASED;
		if (lock != null) {
			lock.release();
		}
	}

	enum State {
		STARTED,
		LOCKED,
		RELEASED
	}
}