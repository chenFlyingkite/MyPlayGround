package com.flyingkite.myplayground.page;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;

import com.flyingkite.myplayground.R;
import com.flyingkite.myplayground.page.host.BaseFragment;
import com.flyingkite.util.Say;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;

public class TextsFragment extends BaseFragment {
    @Override
    protected int getPageLayoutId() {
        return R.layout.page_texts;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        final AutoCompleteTextView auto = (AutoCompleteTextView) findViewById(R.id.editTextAu);
        final AutoCompleteTextView emai = (AutoCompleteTextView) findViewById(R.id.editTextEm);
        TextWatcher watcher1 = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                Say.LogF("will change : %s", s);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Say.LogF("going change : %s", s);
            }

            @Override
            public void afterTextChanged(Editable s) {
                Say.LogF("did change : %s", s);
                auto.showDropDown();
                //emai.showDropDown();
            }
        };
        TextWatcher watcher2 = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                Say.LogF("2 will change : %s", s);
                emai.showDropDown();
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Say.LogF("2 going change : %s", s);
            }

            @Override
            public void afterTextChanged(Editable s) {
                Say.LogF("2 did change : %s", s);
                //auto.showDropDown();
                /*
                findViewById(R.id.clearText).setEnabled(
                        emai
                        //s
                                .length() % 2 == 0
                );
                */
            }
        };
        auto.addTextChangedListener(watcher1);
        ArrayAdapter<String> adp = new ArrayAdapter<>(getActivity(), R.layout.view_autotext,
                new String[] {"a1", "a2", "a3", "a6", "a5", "a4"});
        emai.setAdapter(adp);
        emai.addTextChangedListener(watcher2);
        emai.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                Say.LogF("2 did focus : %s", Say.ox(hasFocus));
                if (hasFocus) {
                    emai.showDropDown();
                }
            }
        });
        emai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Say.LogF("2 did click");
                emai.showDropDown();
            }
        });
        /*
        emai.postDelayed(new Runnable() {
            @Override
            public void run() {
                emai.showDropDown();
            }
        }, 300);
        */

        findViewById(R.id.clearText).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int[] ids = {R.id.editText, R.id.editTextAu, R.id.editTextEm};
                for (int i : ids) {
                    ((TextView) findViewById(i)).setText("");
                }
            }
        });
        //Say.Log("s = " + Arrays.toString(getDeviceEmails()));
    }

    private String[] getDeviceEmails(){
        Pattern emailPattern = Patterns.EMAIL_ADDRESS; // API level 8+
        Account[] accounts = AccountManager.get(getActivity()).getAccounts();
        Set<String> emails = new HashSet<>();
        Say.LogF("%s accounts", accounts.length);
        for (Account account : accounts) {
            Say.Log("acc = " + account);
            if (emailPattern.matcher(account.name).matches()) {
                String possibleEmail = account.name;
                emails.add(possibleEmail);
            }
        }
        return emails.toArray(new String[emails.size()]);
    }
}
