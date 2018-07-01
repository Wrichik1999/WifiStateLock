package com.wrichikbiswas.wifilock;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Process;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;

public class MainActivity extends AppCompatActivity {

	AppCompatButton start;
	AppCompatButton stop;
	AppCompatButton lock;
	AppCompatButton release;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Process.killProcess(1);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
			while (checkSelfPermission(Manifest.permission.WAKE_LOCK) != PackageManager.PERMISSION_GRANTED) {
				requestPermissions(new String[]{Manifest.permission.WAKE_LOCK}, 10);
			}
		}
		AppUtils.activity = this;
		start = findViewById(R.id.starts);
		start.setOnClickListener(v -> {
			startService(new Intent(this, LockService.class));
			start.setEnabled(false);
			stop.setEnabled(true);
			lock.setEnabled(true);
			release.setEnabled(true);
		});
		stop = findViewById(R.id.stops);
		stop.setOnClickListener(v -> {
			AppUtils.service.stop();
			start.setEnabled(true);
			stop.setEnabled(false);
			lock.setEnabled(false);
			release.setEnabled(false);
		});
		lock = findViewById(R.id.lock);
		lock.setOnClickListener(v -> {
			AppUtils.service.lock();
			lock.setEnabled(false);
			release.setEnabled(true);
		});
		release = findViewById(R.id.release);
		release.setOnClickListener(v -> {
			AppUtils.service.release();
			lock.setEnabled(true);
			release.setEnabled(false);
		});
	}
}