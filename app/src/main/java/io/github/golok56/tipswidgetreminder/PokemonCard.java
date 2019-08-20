package io.github.golok56.tipswidgetreminder;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PokemonCard implements Parcelable {
    String name;
    @SerializedName("imageUrl")
    String image;
    String rarity;
    String series;

    public PokemonCard(String name, String image, String rarity, String series) {
        this.name = name;
        this.image = image;
        this.rarity = rarity;
        this.series = series;
    }

    protected PokemonCard(Parcel in) {
        name = in.readString();
        image = in.readString();
        rarity = in.readString();
        series = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(image);
        dest.writeString(rarity);
        dest.writeString(series);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<PokemonCard> CREATOR = new Creator<PokemonCard>() {
        @Override
        public PokemonCard createFromParcel(Parcel in) {
            return new PokemonCard(in);
        }

        @Override
        public PokemonCard[] newArray(int size) {
            return new PokemonCard[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getRarity() {
        return rarity;
    }

    public void setRarity(String rarity) {
        this.rarity = rarity;
    }

    public String getSeries() {
        return series;
    }

    public void setSeries(String series) {
        this.series = series;
    }

    public static class PokemonCardResponse {
        @SerializedName("cards")
        List<PokemonCard> pokemonCards;

        public PokemonCardResponse(List<PokemonCard> pokemonCards) {
            this.pokemonCards = pokemonCards;
        }

        public List<PokemonCard> getPokemonCards() {
            return pokemonCards;
        }

        public void setPokemonCards(List<PokemonCard> pokemonCards) {
            this.pokemonCards = pokemonCards;
        }
    }
}
