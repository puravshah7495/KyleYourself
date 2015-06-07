package com.example.purav.kyleyourself;

import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v7.widget.Toolbar;

public class MainActivity extends AppCompatActivity{
	ViewPageAdapter adapter;
	ViewPager viewPager;
	TabLayout slidingTabLayout;
	Toolbar toolbar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		CharSequence titles[] = {"Generate", "Play"};
		int numOfTabs = 2;

		toolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);

		adapter = new ViewPageAdapter(getSupportFragmentManager(),titles,numOfTabs);

		viewPager = (ViewPager) findViewById(R.id.pager);
		viewPager.setAdapter(adapter);

		slidingTabLayout = (TabLayout) findViewById(R.id.tabs);
		slidingTabLayout.setupWithViewPager(viewPager);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();

		//noinspection SimplifiableIfStatement
		if (id == R.id.action_settings) {
			return true;
		}

		return super.onOptionsItemSelected(item);
	}
}

class ViewPageAdapter extends FragmentStatePagerAdapter {
	CharSequence titles[];
	int numOfTabs;

	public ViewPageAdapter(FragmentManager fm, CharSequence mTitles[], int numOfTabs) {
		super(fm);

		this.titles = mTitles;
		this.numOfTabs = numOfTabs;
	}

	@Override
	public Fragment getItem(int position) {
		if (position == 0) {
			GenerateFragment generateFragment = new GenerateFragment();
			return generateFragment;
		} else {
			GameFragment gameFragment = new GameFragment();
			return gameFragment;
		}
	}

	@Override
	public CharSequence getPageTitle(int position) {
		return titles[position];
	}

	@Override
	public int getCount() {
		return numOfTabs;
	}
}
