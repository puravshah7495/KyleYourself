package com.example.purav.kyleyourself;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.locks.ReentrantLock;


/**
 * A simple {@link Fragment} subclass.
 */
public class GameFragment extends Fragment {
	private boolean running;
	private boolean fastForward;

	private Button mStartButton;
	private Button mSkipButton;
	private TextView mIterationText;
	private RecyclerView mRecyclerView;
	private CustomAdapter customAdapter;
	private List<String> names;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		View view = inflater.inflate(R.layout.fragment_game, container, false);

		mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
		mIterationText = (TextView) view.findViewById(R.id.iterationText);
		mStartButton = (Button) view.findViewById(R.id.startButton);
		mSkipButton = (Button) view.findViewById(R.id.skipButton);

		mSkipButton.setEnabled(false);

		LinearLayoutManager layoutManager = new LinearLayoutManager(view.getContext());
		layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
		layoutManager.scrollToPosition(0);

		mRecyclerView.setLayoutManager(layoutManager);

		names = new ArrayList<>();
		customAdapter = new CustomAdapter(names);
		mRecyclerView.setAdapter(customAdapter);

		mStartButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				startGame();
			}
		});

		mSkipButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				fastForward = true;
			}
		});
		return view;
	}

	public void startGame() {
		Runnable runnable = new Runnable() {
			ReentrantLock lock = new ReentrantLock();
			String name;
			int numIterations;
			@Override
			public void run() {
				if (running)
					return;
				else
					getActivity().runOnUiThread(new Runnable() {
						@Override
						public void run() {
							mSkipButton.setEnabled(true);
							mStartButton.setEnabled(false);
							names.clear();
							customAdapter.notifyDataSetChanged();
						}
					});
					try {
						lock.lock();
						numIterations = 0;
						running = true;
						do {
							name = generateName();
							numIterations++;
							names.add(name);
								//tableList.add(newName);
							if (!fastForward) {
								Thread.sleep(500);
							}
							getActivity().runOnUiThread(new Runnable() {
								@Override
								public void run() {
									customAdapter.notifyDataSetChanged();
									mIterationText.setText("Number of iterations: " + numIterations);
								}
							});
						} while (!name.equals("Kyle Fowler"));
					} catch(Exception e) {
						e.printStackTrace();
					}finally {
						lock.unlock();
						//randomBox.setDisable(false);
						//savedStats.newGamePlayed(numIterations);
						getActivity().runOnUiThread(new Runnable() {
							@Override
							public void run() {
								mSkipButton.setEnabled(false);
								mStartButton.setEnabled(true);
								running = false;
								fastForward = false;
							}
						});
					}
			}
		};

		Thread thread = new Thread(runnable);
		thread.start();
	}

	public String generateName() {
		String [] characters = {"B","C","D","F","G","H","J","K","L","M","N","P","Q","R","S","T","V","W","X","Z"};
		Random random = new Random();
		String firstLetter = characters[random.nextInt(characters.length)];
		String lastLetter = characters[random.nextInt(characters.length)];

		String name = firstLetter + "yle " + lastLetter + "owler";
		return name;
	}
}

class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.SimpleItemViewHolder> {
	private List<String> items;

	public final static class SimpleItemViewHolder extends RecyclerView.ViewHolder {
		TextView label;

		public SimpleItemViewHolder(View itemView) {
			super(itemView);
			label = (TextView) itemView.findViewById(android.R.id.text1);
		}
	}

	public CustomAdapter(List<String> items) {
		this.items = items;
	}

	@Override
	public SimpleItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		View itemView = LayoutInflater.from(parent.getContext())
				.inflate(android.R.layout.simple_list_item_1, parent, false);
		return new SimpleItemViewHolder(itemView);
	}

	@Override
	public void onBindViewHolder(SimpleItemViewHolder holder, int position) {
		String item = items.get(position);
		holder.label.setText(item);
	}

	@Override
	public int getItemCount() {
		return this.items.size();
	}
}
