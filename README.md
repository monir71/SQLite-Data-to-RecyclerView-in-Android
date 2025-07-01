SQLite Custom Helper:

public class SQLiteDataModel extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "contactDB";
    private static final int DATABASE_VERSION = 1;
    private static final String TBL_NAME = "table_contact";
    private static final String TBL_CONTACT_COL_ID = "id";
    private static final String TBL_CONTACT_COL_NAME = "name";
    private static final String TBL_CONTACT_COL_CONTACT_NO = "contact_no";

    public SQLiteDataModel(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TBL_NAME + " ( " + TBL_CONTACT_COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                TBL_CONTACT_COL_NAME + " TEXT NOT NULL, " + TBL_CONTACT_COL_CONTACT_NO + " TEXT NOT NULL)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void addData(String name, String contact_no)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TBL_CONTACT_COL_NAME, name);
        values.put(TBL_CONTACT_COL_CONTACT_NO, contact_no);
        db.insert(TBL_NAME, null, values);
    }

    public ArrayList<ContactModel> fetchContactData() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TBL_NAME, null);

        ArrayList<ContactModel> contactModels = new ArrayList<>();

        while(cursor.moveToNext())
        {
            ContactModel contact = new ContactModel();
            contact.id = cursor.getInt(0);
            contact.name = cursor.getString(1);
            contact.contact_no = cursor.getString(2);

            contactModels.add(contact);
        }

        return contactModels;
    }
}

Data Model:

public class ContactModel {
    int id;
    String name;
    String contact_no;
}

RecyclerView Custom Data Adapter:

public class RecyclerContactAdapter extends RecyclerView.Adapter<RecyclerContactAdapter.ViewHolder> {
    Context context;
    ArrayList<ContactModel> contactList;

    public RecyclerContactAdapter(Context context, ArrayList<ContactModel> contactList)
    {
        this.context = context;
        this.contactList = contactList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.contact_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.col_id.setText(String.valueOf(contactList.get(position).id));
        holder.col_name.setText(contactList.get(position).name);
        holder.col_contact_no.setText(contactList.get(position).contact_no);
    }

    @Override
    public int getItemCount() {
        return contactList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView col_id, col_name, col_contact_no;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            col_id = itemView.findViewById(R.id.col_id);
            col_name = itemView.findViewById(R.id.col_name);
            col_contact_no = itemView.findViewById(R.id.col_contact_no);
        }
    }
}

RecyclerView Layout:

        <androidx.recyclerview.widget.RecyclerView
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:id="@+id/recyclerView"/>

Data View Layout:

        <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:orientation="horizontal">

            <TextView
                android:id="@+id/col_id"
                android:layout_width="50sp"
                android:layout_height="20sp"
                android:layout_marginEnd="10sp"
                android:textAlignment="center"
                android:textColor="#0f0"
                android:textStyle="bold"/>

            <TextView
                android:id="@+id/col_name"
                android:layout_width="150sp"
                android:layout_height="20sp"
                android:layout_marginEnd="10sp"/>

            <TextView
                android:id="@+id/col_contact_no"
                android:layout_width="150sp"
                android:layout_height="20sp"
                android:layout_marginEnd="10sp"/>

    </LinearLayout>

MainActivity.java

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        /*
        try {
            SQLiteDataModel model = new SQLiteDataModel(this);
            //model.addData("Md Moniruzzaman", "01710389323");
            //model.addData("Hamidur Rahman", "017403896542");
            //model.addData("Jahidul Islam", "01841589674");

            ArrayList<ContactModel> data = model.fetchContactData();

            for (int i = 0; i < data.size(); i++)
            {
                Log.d("Contact Data: ", "ID: " + data.get(i).id + ", Name : " + data.get(i).name + ", Contact Number: " +
                        data.get(i).contact_no + "!");
            }

        }
        catch (Exception e)
        {
            Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
        }
        */

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        SQLiteDataModel model = new SQLiteDataModel(this);
        ArrayList<ContactModel> data = model.fetchContactData();

        /*
        ArrayList<ContactModel> data = new ArrayList<>();
        ContactModel model = new ContactModel();
        model.id = 1;
        model.name = "Monir";
        model.contact_no = "01710389323";
        data.add(model);
        data.add(model);
        data.add(model);

        for (int i = 0; i < data.size(); i++)
        {
            Log.d("Contact Data: ", "ID: " + data.get(i).id + ", Name : " + data.get(i).name + ", Contact Number: " +
                    data.get(i).contact_no + "!");
        }
        */

        RecyclerContactAdapter adapter = new RecyclerContactAdapter(this, data);
        recyclerView.setAdapter(adapter);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}

