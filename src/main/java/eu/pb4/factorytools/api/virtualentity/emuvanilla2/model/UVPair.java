package eu.pb4.factorytools.api.virtualentity.emuvanilla2.model;

public record UVPair(float u, float v) {
   public String toString() {
      return "(" + this.u + "," + this.v + ")";
   }

   public static long pack(final float u, final float v) {
      long high = Float.floatToIntBits(u) & 4294967295L;
      long low = Float.floatToIntBits(v) & 4294967295L;
      return high << 32 | low;
   }

   public static float unpackU(final long packedUV) {
      int bits = (int)(packedUV >> 32);
      return Float.intBitsToFloat(bits);
   }

   public static float unpackV(final long packedUV) {
      return Float.intBitsToFloat((int)packedUV);
   }
}
