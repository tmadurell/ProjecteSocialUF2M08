package com.amadurell.projectesocialuf2m08;

import android.os.Bundle;

import androidx.annotation.*;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.*;
import androidx.recyclerview.widget.RecyclerView;

import android.view.*;
import android.widget.SearchView;

import com.amadurell.projectesocialuf2m08.databinding.ViewholderUsuarisBinding;
import com.bumptech.glide.Glide;
import com.amadurell.projectesocialuf2m08.databinding.FragmentCercaBinding;

public class CercaFragment extends Fragment {
    private FragmentCercaBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return (binding = FragmentCercaBinding.inflate(inflater, container, false)).getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        UsuarisViewModel usuarisViewModel = new ViewModelProvider(this).get(UsuarisViewModel.class);

        binding.texto.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) { return false; }

            @Override
            public boolean onQueryTextChange(String s) {
                usuarisViewModel.buscar(s);
                return false;
            }
        });


        ContenidosAdapter contenidosAdapter = new ContenidosAdapter();
        binding.recyclerviewContenidos.setAdapter(contenidosAdapter);

        usuarisViewModel.respuestaMutableLiveData.observe(getViewLifecycleOwner(), new Observer<UsuarisBBDD.Result>() {
            @Override
            public void onChanged(UsuarisBBDD.Result respuesta) {
                contenidosAdapter.establecerListaContenido(respuesta);
                // respuesta.results.forEach(r -> Log.e("ABCD", r.artistName + ", " + r.trackName + ", " + r.artworkUrl100));
            }
        });

    }

    static class ContenidoViewHolder extends RecyclerView.ViewHolder {
        ViewholderUsuarisBinding binding;

        public ContenidoViewHolder(@NonNull ViewholderUsuarisBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    class ContenidosAdapter extends RecyclerView.Adapter<ContenidoViewHolder>{
        UsuarisBBDD.Result pokemonList;

        @NonNull
        @Override
        public ContenidoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new ContenidoViewHolder(ViewholderUsuarisBinding.inflate(getLayoutInflater(), parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull ContenidoViewHolder holder, int position) {
            UsuarisBBDD.Pokemon pokemon = pokemonList.documents.get(position);

            holder.binding.title.setText(pokemon.fields.id_usuari.stringValue);
            holder.binding.artist.setText(pokemon.fields.nom.stringValue);
            Glide.with(requireActivity()).load(pokemon.fields.imatge.stringValue).into(holder.binding.artwork);
        }

        @Override
        public int getItemCount() {
            return pokemonList == null ? 0 : pokemonList.documents.size();
        }

        void establecerListaContenido(UsuarisBBDD.Result pokemonList){
            this.pokemonList = pokemonList;
            notifyDataSetChanged();
        }
    }

}