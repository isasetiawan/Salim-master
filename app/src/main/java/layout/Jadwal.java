package layout;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.example.kucing.salim.ItemJadwalSholat;
import com.example.kucing.salim.JadwalSolatParser;
import com.example.kucing.salim.OnFragmentInteractionListener;
import com.example.kucing.salim.R;
import com.example.kucing.salim.jadwalSholatAdapter;

import org.json.JSONException;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Jadwal#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Jadwal extends Fragment implements OnFragmentInteractionListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    final String TAG = "whats wrong";
    String expire_jason = new SimpleDateFormat("M/yyyy").format(new Date());

    private OnFragmentInteractionListener mListener;


    public Jadwal() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Jadwal.
     */
    // TODO: Rename and change types and number of parameters
    public static Jadwal newInstance(String param1, String param2) {
        Jadwal fragment = new Jadwal();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Bind(R.id.Jaso) ListView jaso;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_jadwal, container, false);
        ButterKnife.bind(this, v);
        SharedPreferences shape = getActivity().getSharedPreferences("Jaso", Context.MODE_PRIVATE);
        String dayoftoday = new SimpleDateFormat("dd").format(new Date());
        String Jason = shape.getString(expire_jason, "Empty");
        if (!Jason.equals("Empty")) {
            try {
                jadwalSholatAdapter adapter = JadwalSolatParser.getAdapter(Jason, dayoftoday, getResources(), getActivity());
                jaso.setAdapter(adapter);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(getContext(),"Tidak Berhasil Mendapatkan Jadwal Sholat Terbaru",Toast.LENGTH_LONG).show();
        }
        jadwalSholatAdapter adapter = (jadwalSholatAdapter) jaso.getAdapter();
        mListener.ChangeAllAboutHeader(adapter.getData());
        return v;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public void onFragmentInteraction(Uri uri){

    }

    @Override
    public void ChangeAllAboutHeader(ArrayList<ItemJadwalSholat> jaso) {

    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
}
