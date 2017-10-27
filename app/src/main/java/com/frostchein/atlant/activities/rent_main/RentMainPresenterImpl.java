package com.frostchein.atlant.activities.rent_main;

import com.frostchein.atlant.activities.base.BasePresenter;
import com.frostchein.atlant.model.rent.Rent;
import com.frostchein.atlant.model.rent.Rent.Amenities;
import com.frostchein.atlant.model.rent.RentCity;
import java.util.ArrayList;
import java.util.Random;
import javax.inject.Inject;

public class RentMainPresenterImpl implements RentMainPresenter, BasePresenter {

  private RentMainView view;
  private ArrayList<Rent> arrayList;
  private ArrayList<RentCity> arrayListCity;
  private int numberCategory = 1;
  private int numberCity = 0;

  @Inject
  RentMainPresenterImpl(RentMainView view) {
    this.view = view;
  }

  @Override
  public void onCreate() {
    onUpdateCity();
    setCity(numberCity);
  }

  @Override
  public void setCity(int numberCity) {
    this.numberCity = numberCity;
    setCategory(numberCategory);
  }

  @Override
  public void setCategory(int numberCategory) {
    this.numberCategory = numberCategory;
    view.setCategory(numberCategory);
    onUpdate();
  }

  @Override
  public void onUpdateCity() {
    arrayListCity = generateRentCity();
    view.setToolbar(arrayListCity);
  }

  @Override
  public void onUpdate() {
    view.onRefreshStart();
    arrayList = generateArrayList(numberCity);
    view.update(arrayList);
    view.onRefreshComplete();
  }

  @Override
  public void onSelected(int pos) {
    view.startActivity(arrayList.get(pos));
  }


  //*******************
  // TEMP GENERATION RANDOM DATA FOR DEMO
  private static ArrayList<Rent> generateArrayList(int numberCity) {
    ArrayList<Rent> arrayList = new ArrayList<>();

    int n = getRandom(1, 20);
    for (int i = 0; i < n; i++) {
      arrayList.add(generateRent(numberCity));
    }
    return arrayList;
  }

  private static Rent generateRent(int numberCit) {
    return new Rent(
        randomName(),
        generateRentCity().get(numberCit).getName(),
        randomAddress(),
        new String[]
            {randomPhoto(), randomPhoto(), randomPhoto(), randomPhoto(), randomPhoto()},
        getRandom(1, 6),//rooms
        getRandom(3, 9),//beds
        getRandom(1, 3),//bath
        getRandom(2, 9),//max guests
        getRandom(30, 120),//price dollars
        "per night",
        randomDescriptions(),
        randomBoolean(),
        getRandom(1, 10000),//like
        new Amenities(randomBoolean(), randomBoolean(), randomBoolean(), randomBoolean()));
  }

  private static ArrayList<RentCity> generateRentCity() {
    ArrayList<RentCity> arrayList = new ArrayList<>();
    arrayList.add(new RentCity("New-York", "https://rent.atlant.io/assets/images/newYork.jpg"));
    arrayList.add(new RentCity("San Francisco", "https://rent.atlant.io/assets/images/sanFrancisco.jpg"));
    arrayList.add(new RentCity("Los Angeles", "https://rent.atlant.io/assets/images/losAngeles.jpg"));
    arrayList.add(new RentCity("Miami", "https://rent.atlant.io/assets/images/miami.jpg"));
    return arrayList;
  }

  private static String randomPhoto() {
    String endpoint = "https://rent.atlant.io/assets/images/";
    String photo[] = new String[]{
        "1_1280x720.jpg",
        "2_1280x720.jpg",
        "3_1280x720.jpg",
        "4_1280x720.jpg",
        "5_1280x720.jpg",
        "6_1280x720.jpg",
        "7_1280x720.jpg",
        "8_1280x720.jpg",
        "9_1280x720.jpg",
        "10_1280x720.jpg",
        "11_1280x720.jpg",
        "12_1280x720.jpg",
        "13_1280x720.jpg",
        "14_1280x720.jpg",
        "15_1280x720.jpg",
        "16_1280x720.jpg",
        "17_1280x720.jpg",
        "18_1280x720.jpg",
        "19_1280x720.jpg",
        "20_1280x720.jpg"
    };
    return endpoint + photo[getRandom(0, 19)];
  }

  private static String randomDescriptions() {
    String descriptions[] = new String[]{
        "A completely furnished room available for lease. I favor long term rentals, but am willing to settle for shorter stays as well",
        "A lovely spacious garden flat. 2 double bedrooms, open arrangement living space, large rooms and an exquisite conservatory. Located close to the railroad terminal and a number of bus stops. Several shops nearby in addition to nice local diners.",
        "This is an amazing studio condo made of brick that has a genuine city feeling. This studio has a recently redesigned lavatory and kitchen with stainless steel appliances. The space fits four people and is halfway situated on a calm road. This unit comes with cable TV and wi-fi. Just minutes away from public transportation. Also, minutes from three bus routes. Come and explore the appeal of the area.",
        "The place is located in the heart of the city center, near to parks and commercial areas. The room is a double shared room fully furnished with two king-sized beds (1 available), a desk, double wardrobe, and an air conditioner."
    };
    int n = getRandom(0, descriptions.length - 1);
    return descriptions[n];
  }

  private static String randomName() {
    String name[] = new String[]{
        "Chamberino", "Groton", "Rosine", "Nettie", "Shasta", "Iberia", "Harviell", "Titanic", "Wilmington", "Spokane"
    };
    int n = getRandom(0, name.length - 1);
    return name[n];
  }

  private static String randomAddress() {
    String address[] = new String[]{
        "466 Grace Court, 1943",
        "121 Village Road, 9366",
        "321 Orange Street, 9805",
        "616 Flatbush Avenue, 9621",
        "698 Murdock Court, 1489",
        "425 Dooley Street, 1327",
        "263 Centre Street, 2305",
        "237 Imlay Street, 8189",
        "946 Bogart Street, 2418",
        "946 Bogart Street, 2418",
        "689 Waldorf Court, 1760",
        "265 Balfour Place, 506",
        "352 Debevoise Street, 9511"
    };
    int n = getRandom(0, address.length - 1);
    return address[n];
  }

  private static boolean randomBoolean() {
    return getRandom(0, 1) == 1;
  }

  private static int getRandom(int min, int max) {
    if (random == null) {
      random = new Random();
    }
    return random.nextInt(max - min + 1) + min;
  }

  private static Random random;

}
