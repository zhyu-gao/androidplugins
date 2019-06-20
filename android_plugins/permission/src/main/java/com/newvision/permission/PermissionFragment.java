package com.newvision.permission;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;

public class PermissionFragment extends Fragment
{
    public static final String PERMISSION_NAMES = "PermissionNames";

    private static final int PERMISSIONS_REQUEST_CODE = 15887;

    private final UnityAndroidPermissions.IPermissionRequestResult m_ResultCallbacks;
    private final Activity m_Activity;

    public PermissionFragment()
    {
        m_ResultCallbacks = null;
        m_Activity = null;
    }

    public PermissionFragment(final Activity activity, final UnityAndroidPermissions.IPermissionRequestResult resultCallbacks)
    {
        m_ResultCallbacks = resultCallbacks;
        m_Activity = activity;
    }

    @android.support.annotation.RequiresApi(api = Build.VERSION_CODES.M)
    @Override public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        if (m_ResultCallbacks == null)
        {
            getFragmentManager().beginTransaction().remove(this).commit();
        }
        else
        {
            String[] permissionNames = getArguments().getStringArray(PERMISSION_NAMES);
            requestPermissions(permissionNames, PERMISSIONS_REQUEST_CODE);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults)
    {
        if (requestCode != PERMISSIONS_REQUEST_CODE)
            return;

        for (int i = 0; i < permissions.length && i < grantResults.length; ++i)
        {
            if (grantResults[i] == PackageManager.PERMISSION_GRANTED)
                m_ResultCallbacks.OnPermissionGranted(permissions[i]);
            else
            {
                if (m_Activity != null &&
                        !m_Activity.shouldShowRequestPermissionRationale(permissions[i]) &&
                        (m_ResultCallbacks instanceof UnityAndroidPermissions.IPermissionRequestResult2))
                {
                    ((UnityAndroidPermissions.IPermissionRequestResult2) m_ResultCallbacks).OnPermissionDeniedAndDontAskAgain(permissions[i]);
                }
                else
                {
                    m_ResultCallbacks.OnPermissionDenied(permissions[i]);
                }
            }
        }

        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.remove(this);
        fragmentTransaction.commit();
    }
}