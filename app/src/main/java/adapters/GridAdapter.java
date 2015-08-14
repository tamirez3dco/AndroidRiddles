package adapters;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.manager.R;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;

import controller.DataBaseAdapter;



/**
 * Created by DELL on 2/13/2015.
 */
public class GridAdapter extends BaseAdapter
{
    //DataBaseAdapter dataBaseAdapter;
    private LayoutInflater mInflater;
    private int size;
    private ArrayList<String> wordIdList  = new ArrayList<String>();
    private ArrayList<Bitmap> imageList = new ArrayList<Bitmap>();
    private ArrayList<String> textList  = new ArrayList<String>();
    private Context context;
    private DataBaseAdapter dataBaseAdapter;
    private Cursor cursor;
    private String audioPath;
    private boolean isEnable;
    private String wordId;

    public static String folderPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/ManagerAudioFiles";//sdcard path


    public GridAdapter(Context context, ArrayList<String> wordIdList, ArrayList<Bitmap> imageList, ArrayList<String> textList) {
        mInflater = LayoutInflater.from(context);
        this.context           = context;
        this.size              = textList.size();
        this.wordIdList        = wordIdList;
        this.imageList         = imageList;
        this.textList          = textList;
    }

    public int getCount() {//no of list view
        return size;
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }

    public String getAudioPath(){
        if (cursor.moveToFirst()) {
            audioPath = cursor.getString(cursor.getColumnIndex("audioPath"));
        }//end if
        return audioPath;
    }

    public View getView(final int position, View convertView, ViewGroup parent)
    {
        final GridAdapter.ViewHolder holder;

        if (convertView == null)
        {
            convertView = mInflater.inflate(R.layout.words_grid_item_layout, null);
            holder = new GridAdapter.ViewHolder();
            holder.wordImageButton    = (ImageView) convertView.findViewById(R.id.wordImageBtn);
            holder.wordText           = (TextView) convertView.findViewById(R.id.leftTextView);

            convertView.setTag(holder);
        }
        else
        {
            holder = (GridAdapter.ViewHolder) convertView.getTag();
        }
        if(imageList.size()!=0) {
            try{
                Bitmap theImage = imageList.get(position);
                holder.wordImageButton.setImageBitmap(theImage);
            }catch (Exception e){
                holder.wordImageButton.setImageResource(R.drawable.ic_launcher);
            }
        }
        else{ //if there is no image set
            holder.wordImageButton.setImageResource(R.drawable.ic_launcher);
        }
        holder.wordText.setText(textList.get(position));
        return convertView;
    }


    public static class ViewHolder {
        public ImageView wordImageButton;
        public TextView wordText;
        public ImageButton playImageButton;

    }
}

