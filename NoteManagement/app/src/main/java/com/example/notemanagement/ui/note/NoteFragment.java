package com.example.notemanagement.ui.note;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.notemanagement.R;
import com.example.notemanagement.adapter.NoteAdapter;
import com.example.notemanagement.database.CategoryDatabase;
import com.example.notemanagement.database.NoteDatabase;
import com.example.notemanagement.database.PriorityDatabase;
import com.example.notemanagement.database.StatusDatabase;
import com.example.notemanagement.entity.Note;
import com.example.notemanagement.entity.Priority;
import com.example.notemanagement.ui.home.HomeViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.sql.Struct;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public class NoteFragment extends Fragment {

    RecyclerView recyclerView;
    List<Note> listNote;
    NoteAdapter noteAdapter;
    FloatingActionButton btnOpen;
    Button btAdd, btClose, btSetPlanDate;
    TextView tvPlanDate;
    Spinner spCategory, spPriority, spStatus;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        listNote = new ArrayList<>();

        View root = inflater.inflate(R.layout.fragment_note, container, false);

        setRecyclerView(root);

        btnOpen = (FloatingActionButton) root.findViewById(R.id.btOpenNoteDialog);
        btnOpen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenNoteDialog(true, -1);
            }
        });

        return root;
    }

    private void setRecyclerView(View root){
        recyclerView = root.findViewById(R.id.rvNote);
        recyclerView.setHasFixedSize(true);

        listNote = NoteDatabase.getInstance(getContext()).NoteDAO().getListNote();
        noteAdapter = new NoteAdapter(getContext(), listNote);

        noteAdapter.setData(listNote);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 1, GridLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(gridLayoutManager);

        recyclerView.setAdapter(noteAdapter);
    }

    public void OpenNoteDialog(boolean isAddNew,@Nullable int position){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        View view = getLayoutInflater().inflate(R.layout.dialog_note, null);
        AlertDialog alertDialog = builder.create();

        alertDialog.setView(view);
        alertDialog.show();

        btSetPlanDate = view.findViewById(R.id.btSetPlanDate);
        btSetPlanDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChooseDate(view);
            }
        });

        btClose = view.findViewById(R.id.btCloseNote);
        btClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.cancel();
            }
        });

        btAdd = view.findViewById(R.id.btAddNote) ;
        if(isAddNew == false)
        {
            EditText edtNoteName = view.findViewById(R.id.edtNoteName);
            Note note = listNote.get(position);
            btAdd.setText("Update");
            edtNoteName.setText(note.getName());

            tvPlanDate = view.findViewById(R.id.tvDate);
            tvPlanDate.setText(note.getPlanDate());

            ChooseCategory(view, note.getCategory());
            ChoosePriority(view, note.getPriority());
            ChooseStatus(view, note.getStatus());
        }
        else {
            ChooseCategory(view, "Select category...");
            ChoosePriority(view, "Select priority...");
            ChooseStatus(view, "Select status...");
        }

        btAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText edtNoteName = view.findViewById(R.id.edtNoteName);
                String noteName = edtNoteName.getText().toString();

                String category = spCategory.getSelectedItem().toString();
                String priority = spPriority.getSelectedItem().toString();;
                String status = spStatus.getSelectedItem().toString();;

                TextView tvPlan = view.findViewById(R.id.tvDate);
                String planDate = tvPlan.getText().toString();

                String createDate = android.text.format.DateFormat.format("yyyy-MM-dd hh:mm:ss", new java.util.Date()).toString();

                if(isAddNew == true){
                    AddNote(new Note(noteName, category,priority,status,planDate, createDate));
                }
                else
                {
                    Note note = listNote.get(position);
                    note.setName(noteName);
                    note.setCategory(category);
                    note.setPriority(priority);
                    note.setStatus(status);
                    note.setPlanDate(planDate);
                    note.setCreateDate(createDate);
                    UpdateNote(note);
                }

                alertDialog.cancel();
            }
        });
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        int position = item.getGroupId();
        switch (item.getItemId()){
            case 0:
                OpenNoteDialog(false, position);
                return true;
            case 1:
                return DeleteNote(position);
        }
        return super.onContextItemSelected(item);
    }

    public void AddNote(Note note){
        NoteDatabase.getInstance(getContext()).NoteDAO().insertNote(note);

        listNote = NoteDatabase.getInstance(getContext()).NoteDAO().getListNote();
        noteAdapter.setData(listNote);
    }

    public void UpdateNote(Note note){
        NoteDatabase.getInstance(getContext()).NoteDAO().updateNote(note);

        listNote = NoteDatabase.getInstance(getContext()).NoteDAO().getListNote();
        noteAdapter.setData(listNote);
    }

    public boolean DeleteNote(int position)
    {
        Note Note = listNote.get(position);
        NoteDatabase.getInstance(getContext()).NoteDAO().deleteNote(Note);
        listNote.remove(position);
        noteAdapter.notifyItemRemoved(position);
        return true;
    }

    public void ChooseCategory(View view, String category){
        //Category
        spCategory = (Spinner) view.findViewById(R.id.spCategory);
        String[] initCategory = {category};

        String[] listCategoryName = CategoryDatabase.getInstance(getContext()).categoryDAO().getCategoryName();

        List listCat = new ArrayList(Arrays.asList(initCategory));
        listCat.addAll(Arrays.asList(listCategoryName));
        Object[] objectCat = listCat.toArray();

        ArrayAdapter adapterCat = new ArrayAdapter(getContext(),android.R.layout.simple_spinner_item, objectCat);
        adapterCat.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spCategory.setAdapter(adapterCat);
    }

    public void ChoosePriority(View view, String priority){
        //Priority
        spPriority = (Spinner) view.findViewById(R.id.spPriority);
        String[] initPriority = {priority};

        String[] listPriorityName = PriorityDatabase.getInstance(getContext()).PriorityDAO().getPriorityName();

        List listPri = new ArrayList(Arrays.asList(initPriority));
        listPri.addAll(Arrays.asList(listPriorityName));
        Object[] objectPri = listPri.toArray();

        ArrayAdapter adapterPri = new ArrayAdapter(getContext(),android.R.layout.simple_spinner_item, objectPri);
        adapterPri.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spPriority.setAdapter(adapterPri);
    }

    public void ChooseStatus(View view, String status){
        //Status
        spStatus = (Spinner) view.findViewById(R.id.spnStatus);
        String[] initStatus = {status};

        String[] listStatusName = StatusDatabase.getInstance(getContext()).StatusDAO().getStatusName();

        List listStatus = new ArrayList(Arrays.asList(initStatus));
        listStatus.addAll(Arrays.asList(listStatusName));
        Object[] objectStatus = listStatus.toArray();

        ArrayAdapter adapterStatus= new ArrayAdapter(getContext(),android.R.layout.simple_spinner_item, objectStatus);
        adapterStatus.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spStatus.setAdapter(adapterStatus);
    }

    public void ChooseDate(View view)
    {
        tvPlanDate = view.findViewById(R.id.tvDate);

        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DATE);
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);

        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                Calendar calendar_date = Calendar.getInstance();
                calendar_date.set(year,month,dayOfMonth);

                String date = DateFormat.format("dd/MM/yyyy", calendar_date).toString();

                tvPlanDate.setText(date);
            }
        }, year, month, day);
        datePickerDialog.setTitle("Select the date");

        datePickerDialog.show();
    }
}