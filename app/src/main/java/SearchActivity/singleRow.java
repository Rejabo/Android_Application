package SearchActivity;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.MainActivites.Chat;
import com.example.MainActivites.R;
import com.example.MainActivites.UserDetails;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;


public class singleRow extends FirebaseRecyclerAdapter <SearchViewModel, singleRow.myviewholder>
{
    public Search se = new Search();

    public singleRow(@NonNull FirebaseRecyclerOptions<SearchViewModel> options) {
        super(options);
    }

    RecyclerView rv_list_frames;


    ListView usersTripPosition;
    TextView usersNameAtPos;
    ArrayList<String> al = new ArrayList<>();
    
    @Override
    protected void onBindViewHolder(@NonNull myviewholder holder, int position, @NonNull SearchViewModel SearchViewModel) {
        holder.name.setText(SearchViewModel.getName());
        holder.course.setText(SearchViewModel.getCourse());
        holder.email.setText(SearchViewModel.getEmail());
        al.add(SearchViewModel.getEmail());

        Log.d("SearchViewModel key:", SearchViewModel.getEmail());
        Log.d("al al al", String.valueOf(al.size()));
        Log.d("al position", String.valueOf(position));
        Glide.with(holder.img.getContext()).load(SearchViewModel.getPurl()).into(holder.img);


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (view.getId() == R.id.button5) {
                    Log.d("HA TY KURWO", "KOSKESH");
                }

            }

        });
    }

    public void onClick(View v) {
        if (v.getId() == R.id.button5){
            //Intent i = new Intent(this, Chat.class);
            //TextView helloTextView = findViewById(R.id.emailtext);
            //helloTextView.setText("set text in hello text view");
            //UserDetails.chatWith = "alenz";
            Log.d("HA K", String.valueOf(rv_list_frames.getChildPosition(v)));


            //startActivity(i);

        }
    }



    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
       View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.singlerow,parent,false);

       return new myviewholder(view);
    }




    class myviewholder extends RecyclerView.ViewHolder
    {
        CircleImageView img;
        TextView name,course,email;
        public myviewholder(@NonNull View itemView)
        {
            super(itemView);
            img=(CircleImageView)itemView.findViewById(R.id.img1);
            name=(TextView)itemView.findViewById(R.id.nametext);
            course=(TextView)itemView.findViewById(R.id.coursetext);
            email=(TextView)itemView.findViewById(R.id.emailtext);


            itemView.findViewById(R.id.button5).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String userLocalText;
                    Log.d("demo userOnclick:", email.getText().toString());
                    System.out.println("HARAILALI_HARAILALO");
                    userLocalText = email.getText().toString();

                    UserDetails.chatWith=userLocalText;

                    Intent i = new Intent(v.getContext(), Chat.class);
                    v.getContext().startActivity(i);


                    //se.CallChatActivity(userLocalText);
                }
            });
        }
    }
}
