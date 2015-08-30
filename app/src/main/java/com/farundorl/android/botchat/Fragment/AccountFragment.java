package com.farundorl.android.botchat.Fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.farundorl.android.botchat.Helper.SharedPreferencesHelper;
import com.farundorl.android.botchat.Model.*;
import com.farundorl.android.botchat.Model.Character;
import com.farundorl.android.botchat.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.functions.Action1;

public class AccountFragment extends Fragment {

    @Bind(R.id.name)
    EditText name;
    @Bind(R.id.name_yomi)
    EditText nameYomi;
    @Bind(R.id.gender_radio)
    RadioGroup genderRadio;
    @Bind(R.id.blood_type)
    Spinner bloodType;
    @Bind(R.id.birthday)
    EditText birthday;
    @Bind(R.id.constellations)
    Spinner constellations;
    @Bind(R.id.place)
    Spinner place;
    @Bind(R.id.mode_radio)
    RadioGroup modeRadio;
    @Bind(R.id.character_radio)
    RadioGroup characterRadio;

    private SharedPreferencesHelper mPref;

    public static final String TAG = AccountFragment.class.getSimpleName();

    public static AccountFragment newInstance() {
        return new AccountFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_account, container, false);
        ButterKnife.bind(this, view);

        mPref = new SharedPreferencesHelper(getActivity());
        initNickName();
        initGenderRadio();
        initBloodType();
        initBirthday();
        initConstellations();
        initPlace();
        initMode();
        initCharacter();

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    private void initNickName() {
        name.setText(mPref.getNickName());
        nameYomi.setText(mPref.getNickNameYomi());
        name.addTextChangedListener(prefTextWatcher(mPref::setNickName));
        nameYomi.addTextChangedListener(prefTextWatcher(mPref::setNickNameYomi));
    }

    private TextWatcher prefTextWatcher(Action1<String> changed) {
        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }

            @Override
            public void afterTextChanged(Editable s) {
                changed.call(s.toString());
            }
        };
    }

    private void initGenderRadio() {
        int id = 0;
        for (Gender gender : Gender.values()) {
            RadioButton button = new RadioButton(getActivity());
            button.setText(gender.toString());
            genderRadio.addView(button);
            if (gender == mPref.getGender()) {
                id = button.getId();
            }
        }
        genderRadio.check(id);
        genderRadio.setOnCheckedChangeListener((RadioGroup group, int checkedId) -> {
            String selected = ((TextView) group.findViewById(checkedId)).getText().toString();
            mPref.setGender(Gender.valueOfName(selected));
        });
    }

    private void initBloodType() {
        int selection = 0;
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        for (BloodType type : BloodType.values()) {
            if(type == mPref.getBloodType()) {
                selection = adapter.getCount();
            }
            adapter.add(type.toString());
        }
        bloodType.setAdapter(adapter);
        bloodType.setSelection(selection);
        bloodType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                BloodType selected = BloodType.valueOfName(adapter.getItem(position));
                mPref.setBloodType(selected);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void initBirthday() {
        birthday.setText(mPref.getBirthday().toString());
        birthday.setOnClickListener(v -> {
            Toast.makeText(getActivity(), "でーとぴっかー！", Toast.LENGTH_SHORT).show();
        });
    }

    private void initConstellations() {
        int selection = 0;
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        for (Constellations star : Constellations.values()) {
            if(star == mPref.getConstellations()) {
                selection = adapter.getCount();
            }
            adapter.add(star.toString());
        }
        constellations.setAdapter(adapter);
        constellations.setSelection(selection);
        constellations.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Constellations selected = Constellations.valueOfName(adapter.getItem(position));
                mPref.setConstellations(selected);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });
    }

    private void initPlace() {
        int selection = 0;
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        for (Place location : Place.values()) {
            if(location == mPref.getPlace()) {
                selection = adapter.getCount();
            }
            adapter.add(location.toString());
        }
        place.setAdapter(adapter);
        place.setSelection(selection);
        place.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Place selected = Place.valueOfName(adapter.getItem(position));
                mPref.setPlace(selected);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void initMode() {
        int id = 0;
        for (Mode mode : Mode.values()) {
            RadioButton button = new RadioButton(getActivity());
            button.setText(mode.getName());
            modeRadio.addView(button);
            if(mode == mPref.getMode()) {
                id = button.getId();
            }
        }
        modeRadio.check(id);
        modeRadio.setOnCheckedChangeListener((RadioGroup group, int checkedId) -> {
            String selected = ((TextView) group.findViewById(checkedId)).getText().toString();
            mPref.setMode(Mode.valueOfName(selected));
        });
    }

    private void initCharacter() {
        int id = 0;
        for (Character character : Character.values()) {
            RadioButton button = new RadioButton(getActivity());
            button.setText(character.getName());
            characterRadio.addView(button);
            if(character == mPref.getCharacter()) {
                id = button.getId();
            }
        }
        characterRadio.check(id);
        characterRadio.setOnCheckedChangeListener((RadioGroup group, int checkedId) -> {
            String selected = ((TextView) group.findViewById(checkedId)).getText().toString();
            mPref.setCharacter(Character.valueOfName(selected));
        });
    }

}