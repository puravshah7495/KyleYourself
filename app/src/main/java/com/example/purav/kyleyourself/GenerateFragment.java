package com.example.purav.kyleyourself;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.Random;


/**
 * A simple {@link Fragment} subclass.
 */
public class GenerateFragment extends Fragment {
	Button mButton;
	TextView mNameView;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		View view = inflater.inflate(R.layout.fragment_generate, container, false);
		mButton = (Button) view.findViewById(R.id.generateButton);
		mNameView = (TextView) view.findViewById(R.id.NameText);

		mButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				String newName = generateName();
				mNameView.setText(newName);
			}
		});

		return view;
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
