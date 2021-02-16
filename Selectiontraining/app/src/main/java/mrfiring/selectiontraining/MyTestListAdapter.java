package mrfiring.selectiontraining;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.stream.Stream;

/**
 * Created by mrfiring on 24.03.17.
 */

public class MyTestListAdapter extends ArrayAdapter<String> {
    private final int resource;
    private final Context context;
    private final String[] objects;

    public MyTestListAdapter(@NonNull Context context, @NonNull String[] objects, int resource ) {
        super(context, R.layout.list_view_center, objects);
        this.context = context;
        this.objects = objects;
        this.resource = resource;

    }




    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(resource, parent, false);
        TextView tvText = (TextView)view.findViewById(R.id.tvText);
        tvText.setText(objects[position]);

        return view;
    }
}
