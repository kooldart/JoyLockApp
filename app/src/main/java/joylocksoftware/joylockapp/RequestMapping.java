//package joylocksoftware.joylockapp;
//
//import java.io.IOException;
//import java.util.ArrayList;
//
///**
// * Created by drin on 29.4.17.
// */
//
//public class RequestMapping {
//    @RequestMapping(method=RequestMethod.GET, value="/placedetails")
//    public BufferedImage PlaceDetails(@PathVariable String placeid) {
//        ArrayList<String> placePhotos = new ArrayList<>();
//
//        OkHttpClient client = new OkHttpClient();
//        Request request = new Request.Builder()
//                .url("https://maps.googleapis.com/maps/api/place/details/json?placeid="+placeid+"&key="+serverKey)
//                .build();
//
//        try {
//            //calling the GoogleAPI to get the PlaceDetails so that I can extract the photo_reference
//            Response response = client.newCall(request).execute();
//            //parsing the response with  Jackson so that I can get the photo_reference
//            ObjectMapper m = new ObjectMapper();
//            JsonNode rootNode = m.readTree(response.body().string());
//            JsonNode resultNode = rootNode.get("result");
//            final JsonNode photoArrayNode = resultNode.get("photos");
//            if (photoArrayNode.isArray()) {
//                for (JsonNode photo: photoArrayNode) {
//                    placePhotos.add(photo.get("photo_reference").textValue());
//                }
//            }
//            //calling the GoogleAPI again so that I can get the photoUrl
//            String photoUrl = String.format("https://maps.googleapis.com/maps/api/place/photo?maxwidth=%s&photoreference=%s&key=%s",
//                    400,
//                    placePhotos.get(0),
//                    serverKey);
//            System.out.println(photoUrl);
//
//            //getting the actual photo
//            Request photoRequest = new Request.Builder().url(photoUrl).build();
//            Response photoResponse = client.newCall(photoRequest).execute();
//            if (!photoResponse.isSuccessful()) throw new IOException("Unexpected code " + response);
//            //returning the photo
//            return ImageIO.read(photoResponse.body().byteStream());
//
//        } catch (IOException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//        return null;
//    }package joylocksoftware.joylockapp;
//
//    @RequestMapping(method=RequestMethod.GET, value="/placedetails")
//    public BufferedImage PlaceDetails(@PathVariable String placeid) {
//        ArrayList<String> placePhotos = new ArrayList<>();
//
//        OkHttpClient client = new OkHttpClient();
//        Request request = new Request.Builder()
//                .url("https://maps.googleapis.com/maps/api/place/details/json?placeid="+placeid+"&key="+serverKey)
//                .build();
//
//        try {
//            //calling the GoogleAPI to get the PlaceDetails so that I can extract the photo_reference
//            Response response = client.newCall(request).execute();
//            //parsing the response with  Jackson so that I can get the photo_reference
//            ObjectMapper m = new ObjectMapper();
//            JsonNode rootNode = m.readTree(response.body().string());
//            JsonNode resultNode = rootNode.get("result");
//            final JsonNode photoArrayNode = resultNode.get("photos");
//            if (photoArrayNode.isArray()) {
//                for (JsonNode photo: photoArrayNode) {
//                    placePhotos.add(photo.get("photo_reference").textValue());
//                }
//            }
//            //calling the GoogleAPI again so that I can get the photoUrl
//            String photoUrl = String.format("https://maps.googleapis.com/maps/api/place/photo?maxwidth=%s&photoreference=%s&key=%s",
//                    400,
//                    placePhotos.get(0),
//                    serverKey);
//            System.out.println(photoUrl);
//
//            //getting the actual photo
//            Request photoRequest = new Request.Builder().url(photoUrl).build();
//            Response photoResponse = client.newCall(photoRequest).execute();
//            if (!photoResponse.isSuccessful()) throw new IOException("Unexpected code " + response);
//            //returning the photo
//            return ImageIO.read(photoResponse.body().byteStream());
//
//        } catch (IOException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//        return null;
//    }
//}
