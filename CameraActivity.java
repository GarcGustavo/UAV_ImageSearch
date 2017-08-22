import android.app.Activity;
import android.hardware.Camera;
import android.hardware.Camera.PictureCallback;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class CameraActivity extends Activity implements PictureCallback
{
	private Camera camera;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(Bundle savedInstanceState);
		setContentView(R.layout.activity_main);
		
		camera = getCameraInstance();
		
	}
	
	protected void getCameraInstance()
	{
		//Queries all the available camera devices
		String[] cameras = getCameraIdList();
		
		
	}
	
	private void requestCameraPermission()
	{
		if(FragmentCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA))
		{
			new ConfirmationDialog().show(getChildFragmentManager(), FRAGMENT_DIALOG);
		}
		else {
			FragmentCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA_PERMISSION);
		}
	}
	
	@Override
	public void onRequestPermissionResult(int reqCode, @NonNull String[] permissions, @NonNull int[] grantResults)
	{
		if(reqCode == REQUEST_CAMERA_PERMISSION)
		{
			if(grantResults.length != 1 || grantResults[0] != PackageManager.PERMISSION_GRANTED)
			{
				ErrorDialog.newInstance(getString(R.string.request_permission)).show(getChildFragmentManager(), FRAGMENT_DIALOG);
			}
		} else {
				super.onRequestPermissionResult(reqCode, permissions, grantResults);
			}
	}
	
	private void openCamera(int width, int height)
	{
		//Check to see if we have the permission
		if(ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
		{
			//since we dont, let's ask again
			requestCameraPermission();
			return;
		}
		
		Activity activity  = getActivity();
		CameraManager manager = (CameraManager) activity.getSystemService(Context.CAMERA_SERVICE);
		
		try{
			/*if(!mCameraOpenCloseLock.tryAcquire(2500, TimeUnit.MILLISECONDS))needs a semaphore
				{
					throw new RuntimeException("Time out waiting to lock camera opening.");
				}
				manager.openCamera(cameraID, mStateCallback, mBackgroundHandler);
			*/
		} catch(CameraAccessException e)
		{
			e.printStackTrace();
		} catch(InterruptedException e)
		{
			throw new RuntimeException("Interrupted while trying to lock thread.",e);
		}
	}
	
	private void closeCamera()
	{
		try{
			/*
				mCameraOpenCloseLock.acquire();
				
				if(null != mCaptureSession)
				{
					mCaptureSession.close();
					mCaptureSession = null;
				}
				if(null != mCameraDevice)
				{
					mCameraDevice.close();
					mCameraDevice = null;
				}
				if(null != mImageReader)
				{
					mImageReader.close();
					mImageReader = null;
				}
			*/
		} catch(InterruptedException e){
			throw new RuntimeException("InterruptedException while closing");
		} finally{
			//mCameraOpenCloseLock.release();
		}
	}
}
