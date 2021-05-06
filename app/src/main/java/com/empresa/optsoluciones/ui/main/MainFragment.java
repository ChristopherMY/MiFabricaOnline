package com.empresa.optsoluciones.ui.main;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.empresa.optsoluciones.R;

public class MainFragment extends Fragment {

    private MainViewModel mViewModel;
    WebView webView;
    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.main_fragment, container, false);
        webView = view.findViewById(R.id.webView);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            webView.getSettings().setSafeBrowsingEnabled(false);
        }

        WebSettings webSettings = webView.getSettings();
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setJavaScriptEnabled(true);

        webSettings.setAppCacheEnabled(false);
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        webSettings.setDatabaseEnabled(false);
        webSettings.setDomStorageEnabled(true);
        webSettings.setGeolocationEnabled(false);
        webSettings.setSaveFormData(false);
        webSettings.setRenderPriority(WebSettings.RenderPriority.HIGH);
        webView.setWebViewClient(new CallBack());
        webView.loadUrl("https://app.mifabricaonline.com");
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(MainViewModel.class);
        // TODO: Use the ViewModel
    }

    private class CallBack extends WebViewClient {
        // For api level bellow 24
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url){

            if(url.startsWith("http")){
                return false;
            }else if(url.startsWith("tel:")){
                handleTelLink(url);

                return true;
            }

            return false;
        }

        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request){
            String url = request.getUrl().toString();

            if(url.startsWith("http")){
                return false;
            }else if(url.startsWith("tel:")){
                // Handle the tel: link
                handleTelLink(url);

                return true;
            }

            return false;
        }
    }

    protected void handleTelLink(String url){
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse(url));

        startActivity(intent);
    }
}