package kr.swkang.snstemplate.utils;

import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.IntDef;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;

import java.io.File;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.List;

/**
 * @author KangSung-Woo
 * @since 2016/08/31
 */
public class AsyncImageFileManager
    extends AsyncTask<Void, Void, Void> {
  private static final String TAG = AsyncImageFileManager.class.getSimpleName();

  public static final int FILE_SAVE  = 0;
  public static final int FILE_LOAD  = 1;
  public static final int FILE_DELTE = 2;

  private OnJobCompleted       listener;
  private List<FileDescriptor> queue;

  public AsyncImageFileManager() {
    init();
  }

  private void init() {
    this.queue = new ArrayList<>();
  }

  public void addFileJob(@JobDescriptor int fileJob, @Nullable String targetFilepath) {
    if (!TextUtils.isEmpty(targetFilepath)) {
      File f = createFile(targetFilepath);
      if (f != null) {
        queue.add(new FileDescriptor(fileJob, f));
      }
    }
  }

  public void addFileJobs(@JobDescriptor int fileJob, @NonNull List<String> filePaths) {
    if (filePaths.size() > 0) {
      for (String path : filePaths) {
        File f = createFile(path);
        if (f != null) {
          queue.add(new FileDescriptor(fileJob, f));
        }
      }
    }
  }

  private File createFile(@Nullable String targetFilepath) {
    if (TextUtils.isEmpty(targetFilepath)) {
      Log.e(TAG, "// addQueue() // onError // target file path is null or Empty..");
    }
    else {
      return new File(targetFilepath);
    }
    return null;
  }

  @Override
  protected Void doInBackground(Void... voids) {
    return null;
  }

  @Override
  protected void onPostExecute(Void aVoid) {
    if (listener != null) {
      listener.onJobCompleted();
    }
  }

  private class FileDescriptor {
    @JobDescriptor
    private int  descriptor;
    private File file;

    public FileDescriptor(@JobDescriptor int descriptor, @Nullable File file) {
      this.descriptor = descriptor;
      this.file = file;
    }

    public int getDescriptor() {
      return descriptor;
    }

    public void setDescriptor(int descriptor) {
      this.descriptor = descriptor;
    }

    public File getFile() {
      return file;
    }

    public void setFile(File file) {
      this.file = file;
    }
  }

  public interface OnJobCompleted {
    void onJobCompleted();
  }

  @IntDef(flag = true, value = {
      FILE_SAVE,
      FILE_LOAD,
      FILE_DELTE
  })
  @Retention(RetentionPolicy.SOURCE)
  public @interface JobDescriptor {
  }

}
