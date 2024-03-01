package eu.pb4.factorytools.api.util;

import org.joml.*;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.text.NumberFormat;

public final class ThreadedMatrix4f extends Matrix4f {
    private final ThreadLocal<Matrix4f> matrix = ThreadLocal.withInitial(Matrix4f::new);

    public Matrix4f getMat() {
        return this.matrix.get();
    }

    @Override
    public Matrix4f assume(int properties) {
        return this.matrix.get().assume(properties);
    }

    @Override
    public Matrix4f determineProperties() {
        return this.matrix.get().determineProperties();
    }

    @Override
    public int properties() {
        return this.matrix.get().properties();
    }

    @Override
    public float m00() {
        return this.matrix.get().m00();
    }

    @Override
    public float m01() {
        return this.matrix.get().m01();
    }

    @Override
    public float m02() {
        return this.matrix.get().m02();
    }

    @Override
    public float m03() {
        return this.matrix.get().m03();
    }

    @Override
    public float m10() {
        return this.matrix.get().m10();
    }

    @Override
    public float m11() {
        return this.matrix.get().m11();
    }

    @Override
    public float m12() {
        return this.matrix.get().m12();
    }

    @Override
    public float m13() {
        return this.matrix.get().m13();
    }

    @Override
    public float m20() {
        return this.matrix.get().m20();
    }

    @Override
    public float m21() {
        return this.matrix.get().m21();
    }

    @Override
    public float m22() {
        return this.matrix.get().m22();
    }

    @Override
    public float m23() {
        return this.matrix.get().m23();
    }

    @Override
    public float m30() {
        return this.matrix.get().m30();
    }

    @Override
    public float m31() {
        return this.matrix.get().m31();
    }

    @Override
    public float m32() {
        return this.matrix.get().m32();
    }

    @Override
    public float m33() {
        return this.matrix.get().m33();
    }

    @Override
    public Matrix4f m00(float m00) {
        return this.matrix.get().m00(m00);
    }

    @Override
    public Matrix4f m01(float m01) {
        return this.matrix.get().m01(m01);
    }

    @Override
    public Matrix4f m02(float m02) {
        return this.matrix.get().m02(m02);
    }

    @Override
    public Matrix4f m03(float m03) {
        return this.matrix.get().m03(m03);
    }

    @Override
    public Matrix4f m10(float m10) {
        return this.matrix.get().m10(m10);
    }

    @Override
    public Matrix4f m11(float m11) {
        return this.matrix.get().m11(m11);
    }

    @Override
    public Matrix4f m12(float m12) {
        return this.matrix.get().m12(m12);
    }

    @Override
    public Matrix4f m13(float m13) {
        return this.matrix.get().m13(m13);
    }

    @Override
    public Matrix4f m20(float m20) {
        return this.matrix.get().m20(m20);
    }

    @Override
    public Matrix4f m21(float m21) {
        return this.matrix.get().m21(m21);
    }

    @Override
    public Matrix4f m22(float m22) {
        return this.matrix.get().m22(m22);
    }

    @Override
    public Matrix4f m23(float m23) {
        return this.matrix.get().m23(m23);
    }

    @Override
    public Matrix4f m30(float m30) {
        return this.matrix.get().m30(m30);
    }

    @Override
    public Matrix4f m31(float m31) {
        return this.matrix.get().m31(m31);
    }

    @Override
    public Matrix4f m32(float m32) {
        return this.matrix.get().m32(m32);
    }

    @Override
    public Matrix4f m33(float m33) {
        return this.matrix.get().m33(m33);
    }

    @Override
    public Matrix4f identity() {
        return this.matrix.get().identity();
    }

    @Override
    public Matrix4f set(Matrix4fc m) {
        return this.matrix.get().set(m);
    }

    @Override
    public Matrix4f setTransposed(Matrix4fc m) {
        return this.matrix.get().setTransposed(m);
    }

    @Override
    public Matrix4f set(Matrix4x3fc m) {
        return this.matrix.get().set(m);
    }

    @Override
    public Matrix4f set(Matrix4dc m) {
        return this.matrix.get().set(m);
    }

    @Override
    public Matrix4f set(Matrix3fc mat) {
        return this.matrix.get().set(mat);
    }

    @Override
    public Matrix4f set(AxisAngle4f axisAngle) {
        return this.matrix.get().set(axisAngle);
    }

    @Override
    public Matrix4f set(AxisAngle4d axisAngle) {
        return this.matrix.get().set(axisAngle);
    }

    @Override
    public Matrix4f set(Quaternionfc q) {
        return this.matrix.get().set(q);
    }

    @Override
    public Matrix4f set(Quaterniondc q) {
        return this.matrix.get().set(q);
    }

    @Override
    public Matrix4f set3x3(Matrix4f mat) {
        return this.matrix.get().set3x3(mat);
    }

    @Override
    public Matrix4f set4x3(Matrix4x3fc mat) {
        return this.matrix.get().set4x3(mat);
    }

    @Override
    public Matrix4f set4x3(Matrix4f mat) {
        return this.matrix.get().set4x3(mat);
    }

    @Override
    public Matrix4f mul(Matrix4fc right) {
        return this.matrix.get().mul(right);
    }

    @Override
    public Matrix4f mul(Matrix4fc right, Matrix4f dest) {
        return this.matrix.get().mul(right, dest);
    }

    @Override
    public Matrix4f mul0(Matrix4fc right) {
        return this.matrix.get().mul0(right);
    }

    @Override
    public Matrix4f mul0(Matrix4fc right, Matrix4f dest) {
        return this.matrix.get().mul0(right, dest);
    }

    @Override
    public Matrix4f mul(float r00, float r01, float r02, float r03, float r10, float r11, float r12, float r13, float r20, float r21, float r22, float r23, float r30, float r31, float r32, float r33) {
        return this.matrix.get().mul(r00, r01, r02, r03, r10, r11, r12, r13, r20, r21, r22, r23, r30, r31, r32, r33);
    }

    @Override
    public Matrix4f mul(float r00, float r01, float r02, float r03, float r10, float r11, float r12, float r13, float r20, float r21, float r22, float r23, float r30, float r31, float r32, float r33, Matrix4f dest) {
        return this.matrix.get().mul(r00, r01, r02, r03, r10, r11, r12, r13, r20, r21, r22, r23, r30, r31, r32, r33, dest);
    }

    @Override
    public Matrix4f mul3x3(float r00, float r01, float r02, float r10, float r11, float r12, float r20, float r21, float r22) {
        return this.matrix.get().mul3x3(r00, r01, r02, r10, r11, r12, r20, r21, r22);
    }

    @Override
    public Matrix4f mul3x3(float r00, float r01, float r02, float r10, float r11, float r12, float r20, float r21, float r22, Matrix4f dest) {
        return this.matrix.get().mul3x3(r00, r01, r02, r10, r11, r12, r20, r21, r22, dest);
    }

    @Override
    public Matrix4f mulLocal(Matrix4fc left) {
        return this.matrix.get().mulLocal(left);
    }

    @Override
    public Matrix4f mulLocal(Matrix4fc left, Matrix4f dest) {
        return this.matrix.get().mulLocal(left, dest);
    }

    @Override
    public Matrix4f mulLocalAffine(Matrix4fc left) {
        return this.matrix.get().mulLocalAffine(left);
    }

    @Override
    public Matrix4f mulLocalAffine(Matrix4fc left, Matrix4f dest) {
        return this.matrix.get().mulLocalAffine(left, dest);
    }

    @Override
    public Matrix4f mul(Matrix4x3fc right) {
        return this.matrix.get().mul(right);
    }

    @Override
    public Matrix4f mul(Matrix4x3fc right, Matrix4f dest) {
        return this.matrix.get().mul(right, dest);
    }

    @Override
    public Matrix4f mul(Matrix3x2fc right) {
        return this.matrix.get().mul(right);
    }

    @Override
    public Matrix4f mul(Matrix3x2fc right, Matrix4f dest) {
        return this.matrix.get().mul(right, dest);
    }

    @Override
    public Matrix4f mulPerspectiveAffine(Matrix4fc view) {
        return this.matrix.get().mulPerspectiveAffine(view);
    }

    @Override
    public Matrix4f mulPerspectiveAffine(Matrix4fc view, Matrix4f dest) {
        return this.matrix.get().mulPerspectiveAffine(view, dest);
    }

    @Override
    public Matrix4f mulPerspectiveAffine(Matrix4x3fc view) {
        return this.matrix.get().mulPerspectiveAffine(view);
    }

    @Override
    public Matrix4f mulPerspectiveAffine(Matrix4x3fc view, Matrix4f dest) {
        return this.matrix.get().mulPerspectiveAffine(view, dest);
    }

    @Override
    public Matrix4f mulAffineR(Matrix4fc right) {
        return this.matrix.get().mulAffineR(right);
    }

    @Override
    public Matrix4f mulAffineR(Matrix4fc right, Matrix4f dest) {
        return this.matrix.get().mulAffineR(right, dest);
    }

    @Override
    public Matrix4f mulAffine(Matrix4fc right) {
        return this.matrix.get().mulAffine(right);
    }

    @Override
    public Matrix4f mulAffine(Matrix4fc right, Matrix4f dest) {
        return this.matrix.get().mulAffine(right, dest);
    }

    @Override
    public Matrix4f mulTranslationAffine(Matrix4fc right, Matrix4f dest) {
        return this.matrix.get().mulTranslationAffine(right, dest);
    }

    @Override
    public Matrix4f mulOrthoAffine(Matrix4fc view) {
        return this.matrix.get().mulOrthoAffine(view);
    }

    @Override
    public Matrix4f mulOrthoAffine(Matrix4fc view, Matrix4f dest) {
        return this.matrix.get().mulOrthoAffine(view, dest);
    }

    @Override
    public Matrix4f fma4x3(Matrix4fc other, float otherFactor) {
        return this.matrix.get().fma4x3(other, otherFactor);
    }

    @Override
    public Matrix4f fma4x3(Matrix4fc other, float otherFactor, Matrix4f dest) {
        return this.matrix.get().fma4x3(other, otherFactor, dest);
    }

    @Override
    public Matrix4f add(Matrix4fc other) {
        return this.matrix.get().add(other);
    }

    @Override
    public Matrix4f add(Matrix4fc other, Matrix4f dest) {
        return this.matrix.get().add(other, dest);
    }

    @Override
    public Matrix4f sub(Matrix4fc subtrahend) {
        return this.matrix.get().sub(subtrahend);
    }

    @Override
    public Matrix4f sub(Matrix4fc subtrahend, Matrix4f dest) {
        return this.matrix.get().sub(subtrahend, dest);
    }

    @Override
    public Matrix4f mulComponentWise(Matrix4fc other) {
        return this.matrix.get().mulComponentWise(other);
    }

    @Override
    public Matrix4f mulComponentWise(Matrix4fc other, Matrix4f dest) {
        return this.matrix.get().mulComponentWise(other, dest);
    }

    @Override
    public Matrix4f add4x3(Matrix4fc other) {
        return this.matrix.get().add4x3(other);
    }

    @Override
    public Matrix4f add4x3(Matrix4fc other, Matrix4f dest) {
        return this.matrix.get().add4x3(other, dest);
    }

    @Override
    public Matrix4f sub4x3(Matrix4f subtrahend) {
        return this.matrix.get().sub4x3(subtrahend);
    }

    @Override
    public Matrix4f sub4x3(Matrix4fc subtrahend, Matrix4f dest) {
        return this.matrix.get().sub4x3(subtrahend, dest);
    }

    @Override
    public Matrix4f mul4x3ComponentWise(Matrix4fc other) {
        return this.matrix.get().mul4x3ComponentWise(other);
    }

    @Override
    public Matrix4f mul4x3ComponentWise(Matrix4fc other, Matrix4f dest) {
        return this.matrix.get().mul4x3ComponentWise(other, dest);
    }

    @Override
    public Matrix4f set(float m00, float m01, float m02, float m03, float m10, float m11, float m12, float m13, float m20, float m21, float m22, float m23, float m30, float m31, float m32, float m33) {
        return this.matrix.get().set(m00, m01, m02, m03, m10, m11, m12, m13, m20, m21, m22, m23, m30, m31, m32, m33);
    }

    @Override
    public Matrix4f set(float[] m, int off) {
        return this.matrix.get().set(m, off);
    }

    @Override
    public Matrix4f set(float[] m) {
        return this.matrix.get().set(m);
    }

    @Override
    public Matrix4f setTransposed(float[] m, int off) {
        return this.matrix.get().setTransposed(m, off);
    }

    @Override
    public Matrix4f setTransposed(float[] m) {
        return this.matrix.get().setTransposed(m);
    }

    @Override
    public Matrix4f set(FloatBuffer buffer) {
        return this.matrix.get().set(buffer);
    }

    @Override
    public Matrix4f set(ByteBuffer buffer) {
        return this.matrix.get().set(buffer);
    }

    @Override
    public Matrix4f set(int index, FloatBuffer buffer) {
        return this.matrix.get().set(index, buffer);
    }

    @Override
    public Matrix4f set(int index, ByteBuffer buffer) {
        return this.matrix.get().set(index, buffer);
    }

    @Override
    public Matrix4f setTransposed(FloatBuffer buffer) {
        return this.matrix.get().setTransposed(buffer);
    }

    @Override
    public Matrix4f setTransposed(ByteBuffer buffer) {
        return this.matrix.get().setTransposed(buffer);
    }

    @Override
    public Matrix4f setFromAddress(long address) {
        return this.matrix.get().setFromAddress(address);
    }

    @Override
    public Matrix4f setTransposedFromAddress(long address) {
        return this.matrix.get().setTransposedFromAddress(address);
    }

    @Override
    public Matrix4f set(Vector4fc col0, Vector4fc col1, Vector4fc col2, Vector4fc col3) {
        return this.matrix.get().set(col0, col1, col2, col3);
    }

    @Override
    public float determinant() {
        return this.matrix.get().determinant();
    }

    @Override
    public float determinant3x3() {
        return this.matrix.get().determinant3x3();
    }

    @Override
    public float determinantAffine() {
        return this.matrix.get().determinantAffine();
    }

    @Override
    public Matrix4f invert(Matrix4f dest) {
        return this.matrix.get().invert(dest);
    }

    @Override
    public Matrix4f invert() {
        return this.matrix.get().invert();
    }

    @Override
    public Matrix4f invertPerspective(Matrix4f dest) {
        return this.matrix.get().invertPerspective(dest);
    }

    @Override
    public Matrix4f invertPerspective() {
        return this.matrix.get().invertPerspective();
    }

    @Override
    public Matrix4f invertFrustum(Matrix4f dest) {
        return this.matrix.get().invertFrustum(dest);
    }

    @Override
    public Matrix4f invertFrustum() {
        return this.matrix.get().invertFrustum();
    }

    @Override
    public Matrix4f invertOrtho(Matrix4f dest) {
        return this.matrix.get().invertOrtho(dest);
    }

    @Override
    public Matrix4f invertOrtho() {
        return this.matrix.get().invertOrtho();
    }

    @Override
    public Matrix4f invertPerspectiveView(Matrix4fc view, Matrix4f dest) {
        return this.matrix.get().invertPerspectiveView(view, dest);
    }

    @Override
    public Matrix4f invertPerspectiveView(Matrix4x3fc view, Matrix4f dest) {
        return this.matrix.get().invertPerspectiveView(view, dest);
    }

    @Override
    public Matrix4f invertAffine(Matrix4f dest) {
        return this.matrix.get().invertAffine(dest);
    }

    @Override
    public Matrix4f invertAffine() {
        return this.matrix.get().invertAffine();
    }

    @Override
    public Matrix4f transpose(Matrix4f dest) {
        return this.matrix.get().transpose(dest);
    }

    @Override
    public Matrix4f transpose3x3() {
        return this.matrix.get().transpose3x3();
    }

    @Override
    public Matrix4f transpose3x3(Matrix4f dest) {
        return this.matrix.get().transpose3x3(dest);
    }

    @Override
    public Matrix3f transpose3x3(Matrix3f dest) {
        return this.matrix.get().transpose3x3(dest);
    }

    @Override
    public Matrix4f transpose() {
        return this.matrix.get().transpose();
    }

    @Override
    public Matrix4f translation(float x, float y, float z) {
        return this.matrix.get().translation(x, y, z);
    }

    @Override
    public Matrix4f translation(Vector3fc offset) {
        return this.matrix.get().translation(offset);
    }

    @Override
    public Matrix4f setTranslation(float x, float y, float z) {
        return this.matrix.get().setTranslation(x, y, z);
    }

    @Override
    public Matrix4f setTranslation(Vector3fc xyz) {
        return this.matrix.get().setTranslation(xyz);
    }

    @Override
    public Vector3f getTranslation(Vector3f dest) {
        return this.matrix.get().getTranslation(dest);
    }

    @Override
    public Vector3f getScale(Vector3f dest) {
        return this.matrix.get().getScale(dest);
    }

    @Override
    public String toString() {
        return this.matrix.get().toString();
    }

    @Override
    public String toString(NumberFormat formatter) {
        return this.matrix.get().toString(formatter);
    }

    @Override
    public Matrix4f get(Matrix4f dest) {
        return this.matrix.get().get(dest);
    }

    @Override
    public Matrix4x3f get4x3(Matrix4x3f dest) {
        return this.matrix.get().get4x3(dest);
    }

    @Override
    public Matrix4d get(Matrix4d dest) {
        return this.matrix.get().get(dest);
    }

    @Override
    public Matrix3f get3x3(Matrix3f dest) {
        return this.matrix.get().get3x3(dest);
    }

    @Override
    public Matrix3d get3x3(Matrix3d dest) {
        return this.matrix.get().get3x3(dest);
    }

    @Override
    public AxisAngle4f getRotation(AxisAngle4f dest) {
        return this.matrix.get().getRotation(dest);
    }

    @Override
    public AxisAngle4d getRotation(AxisAngle4d dest) {
        return this.matrix.get().getRotation(dest);
    }

    @Override
    public Quaternionf getUnnormalizedRotation(Quaternionf dest) {
        return this.matrix.get().getUnnormalizedRotation(dest);
    }

    @Override
    public Quaternionf getNormalizedRotation(Quaternionf dest) {
        return this.matrix.get().getNormalizedRotation(dest);
    }

    @Override
    public Quaterniond getUnnormalizedRotation(Quaterniond dest) {
        return this.matrix.get().getUnnormalizedRotation(dest);
    }

    @Override
    public Quaterniond getNormalizedRotation(Quaterniond dest) {
        return this.matrix.get().getNormalizedRotation(dest);
    }

    @Override
    public FloatBuffer get(FloatBuffer buffer) {
        return this.matrix.get().get(buffer);
    }

    @Override
    public FloatBuffer get(int index, FloatBuffer buffer) {
        return this.matrix.get().get(index, buffer);
    }

    @Override
    public ByteBuffer get(ByteBuffer buffer) {
        return this.matrix.get().get(buffer);
    }

    @Override
    public ByteBuffer get(int index, ByteBuffer buffer) {
        return this.matrix.get().get(index, buffer);
    }

    @Override
    public FloatBuffer get4x3(FloatBuffer buffer) {
        return this.matrix.get().get4x3(buffer);
    }

    @Override
    public FloatBuffer get4x3(int index, FloatBuffer buffer) {
        return this.matrix.get().get4x3(index, buffer);
    }

    @Override
    public ByteBuffer get4x3(ByteBuffer buffer) {
        return this.matrix.get().get4x3(buffer);
    }

    @Override
    public ByteBuffer get4x3(int index, ByteBuffer buffer) {
        return this.matrix.get().get4x3(index, buffer);
    }

    @Override
    public FloatBuffer get3x4(FloatBuffer buffer) {
        return this.matrix.get().get3x4(buffer);
    }

    @Override
    public FloatBuffer get3x4(int index, FloatBuffer buffer) {
        return this.matrix.get().get3x4(index, buffer);
    }

    @Override
    public ByteBuffer get3x4(ByteBuffer buffer) {
        return this.matrix.get().get3x4(buffer);
    }

    @Override
    public ByteBuffer get3x4(int index, ByteBuffer buffer) {
        return this.matrix.get().get3x4(index, buffer);
    }

    @Override
    public FloatBuffer getTransposed(FloatBuffer buffer) {
        return this.matrix.get().getTransposed(buffer);
    }

    @Override
    public FloatBuffer getTransposed(int index, FloatBuffer buffer) {
        return this.matrix.get().getTransposed(index, buffer);
    }

    @Override
    public ByteBuffer getTransposed(ByteBuffer buffer) {
        return this.matrix.get().getTransposed(buffer);
    }

    @Override
    public ByteBuffer getTransposed(int index, ByteBuffer buffer) {
        return this.matrix.get().getTransposed(index, buffer);
    }

    @Override
    public FloatBuffer get4x3Transposed(FloatBuffer buffer) {
        return this.matrix.get().get4x3Transposed(buffer);
    }

    @Override
    public FloatBuffer get4x3Transposed(int index, FloatBuffer buffer) {
        return this.matrix.get().get4x3Transposed(index, buffer);
    }

    @Override
    public ByteBuffer get4x3Transposed(ByteBuffer buffer) {
        return this.matrix.get().get4x3Transposed(buffer);
    }

    @Override
    public ByteBuffer get4x3Transposed(int index, ByteBuffer buffer) {
        return this.matrix.get().get4x3Transposed(index, buffer);
    }

    @Override
    public Matrix4fc getToAddress(long address) {
        return this.matrix.get().getToAddress(address);
    }

    @Override
    public float[] get(float[] arr, int offset) {
        return this.matrix.get().get(arr, offset);
    }

    @Override
    public float[] get(float[] arr) {
        return this.matrix.get().get(arr);
    }

    @Override
    public Matrix4f zero() {
        return this.matrix.get().zero();
    }

    @Override
    public Matrix4f scaling(float factor) {
        return this.matrix.get().scaling(factor);
    }

    @Override
    public Matrix4f scaling(float x, float y, float z) {
        return this.matrix.get().scaling(x, y, z);
    }

    @Override
    public Matrix4f scaling(Vector3fc xyz) {
        return this.matrix.get().scaling(xyz);
    }

    @Override
    public Matrix4f rotation(float angle, Vector3fc axis) {
        return this.matrix.get().rotation(angle, axis);
    }

    @Override
    public Matrix4f rotation(AxisAngle4f axisAngle) {
        return this.matrix.get().rotation(axisAngle);
    }

    @Override
    public Matrix4f rotation(float angle, float x, float y, float z) {
        return this.matrix.get().rotation(angle, x, y, z);
    }

    @Override
    public Matrix4f rotationX(float ang) {
        return this.matrix.get().rotationX(ang);
    }

    @Override
    public Matrix4f rotationY(float ang) {
        return this.matrix.get().rotationY(ang);
    }

    @Override
    public Matrix4f rotationZ(float ang) {
        return this.matrix.get().rotationZ(ang);
    }

    @Override
    public Matrix4f rotationTowardsXY(float dirX, float dirY) {
        return this.matrix.get().rotationTowardsXY(dirX, dirY);
    }

    @Override
    public Matrix4f rotationXYZ(float angleX, float angleY, float angleZ) {
        return this.matrix.get().rotationXYZ(angleX, angleY, angleZ);
    }

    @Override
    public Matrix4f rotationZYX(float angleZ, float angleY, float angleX) {
        return this.matrix.get().rotationZYX(angleZ, angleY, angleX);
    }

    @Override
    public Matrix4f rotationYXZ(float angleY, float angleX, float angleZ) {
        return this.matrix.get().rotationYXZ(angleY, angleX, angleZ);
    }

    @Override
    public Matrix4f setRotationXYZ(float angleX, float angleY, float angleZ) {
        return this.matrix.get().setRotationXYZ(angleX, angleY, angleZ);
    }

    @Override
    public Matrix4f setRotationZYX(float angleZ, float angleY, float angleX) {
        return this.matrix.get().setRotationZYX(angleZ, angleY, angleX);
    }

    @Override
    public Matrix4f setRotationYXZ(float angleY, float angleX, float angleZ) {
        return this.matrix.get().setRotationYXZ(angleY, angleX, angleZ);
    }

    @Override
    public Matrix4f rotation(Quaternionfc quat) {
        return this.matrix.get().rotation(quat);
    }

    @Override
    public Matrix4f translationRotateScale(float tx, float ty, float tz, float qx, float qy, float qz, float qw, float sx, float sy, float sz) {
        return this.matrix.get().translationRotateScale(tx, ty, tz, qx, qy, qz, qw, sx, sy, sz);
    }

    @Override
    public Matrix4f translationRotateScale(Vector3fc translation, Quaternionfc quat, Vector3fc scale) {
        return this.matrix.get().translationRotateScale(translation, quat, scale);
    }

    @Override
    public Matrix4f translationRotateScale(float tx, float ty, float tz, float qx, float qy, float qz, float qw, float scale) {
        return this.matrix.get().translationRotateScale(tx, ty, tz, qx, qy, qz, qw, scale);
    }

    @Override
    public Matrix4f translationRotateScale(Vector3fc translation, Quaternionfc quat, float scale) {
        return this.matrix.get().translationRotateScale(translation, quat, scale);
    }

    @Override
    public Matrix4f translationRotateScaleInvert(float tx, float ty, float tz, float qx, float qy, float qz, float qw, float sx, float sy, float sz) {
        return this.matrix.get().translationRotateScaleInvert(tx, ty, tz, qx, qy, qz, qw, sx, sy, sz);
    }

    @Override
    public Matrix4f translationRotateScaleInvert(Vector3fc translation, Quaternionfc quat, Vector3fc scale) {
        return this.matrix.get().translationRotateScaleInvert(translation, quat, scale);
    }

    @Override
    public Matrix4f translationRotateScaleInvert(Vector3fc translation, Quaternionfc quat, float scale) {
        return this.matrix.get().translationRotateScaleInvert(translation, quat, scale);
    }

    @Override
    public Matrix4f translationRotateScaleMulAffine(float tx, float ty, float tz, float qx, float qy, float qz, float qw, float sx, float sy, float sz, Matrix4f m) {
        return this.matrix.get().translationRotateScaleMulAffine(tx, ty, tz, qx, qy, qz, qw, sx, sy, sz, m);
    }

    @Override
    public Matrix4f translationRotateScaleMulAffine(Vector3fc translation, Quaternionfc quat, Vector3fc scale, Matrix4f m) {
        return this.matrix.get().translationRotateScaleMulAffine(translation, quat, scale, m);
    }

    @Override
    public Matrix4f translationRotate(float tx, float ty, float tz, float qx, float qy, float qz, float qw) {
        return this.matrix.get().translationRotate(tx, ty, tz, qx, qy, qz, qw);
    }

    @Override
    public Matrix4f translationRotate(float tx, float ty, float tz, Quaternionfc quat) {
        return this.matrix.get().translationRotate(tx, ty, tz, quat);
    }

    @Override
    public Matrix4f translationRotate(Vector3fc translation, Quaternionfc quat) {
        return this.matrix.get().translationRotate(translation, quat);
    }

    @Override
    public Matrix4f translationRotateInvert(float tx, float ty, float tz, float qx, float qy, float qz, float qw) {
        return this.matrix.get().translationRotateInvert(tx, ty, tz, qx, qy, qz, qw);
    }

    @Override
    public Matrix4f translationRotateInvert(Vector3fc translation, Quaternionfc quat) {
        return this.matrix.get().translationRotateInvert(translation, quat);
    }

    @Override
    public Matrix4f set3x3(Matrix3fc mat) {
        return this.matrix.get().set3x3(mat);
    }

    @Override
    public Vector4f transform(Vector4f v) {
        return this.matrix.get().transform(v);
    }

    @Override
    public Vector4f transform(Vector4fc v, Vector4f dest) {
        return this.matrix.get().transform(v, dest);
    }

    @Override
    public Vector4f transform(float x, float y, float z, float w, Vector4f dest) {
        return this.matrix.get().transform(x, y, z, w, dest);
    }

    @Override
    public Vector4f transformTranspose(Vector4f v) {
        return this.matrix.get().transformTranspose(v);
    }

    @Override
    public Vector4f transformTranspose(Vector4fc v, Vector4f dest) {
        return this.matrix.get().transformTranspose(v, dest);
    }

    @Override
    public Vector4f transformTranspose(float x, float y, float z, float w, Vector4f dest) {
        return this.matrix.get().transformTranspose(x, y, z, w, dest);
    }

    @Override
    public Vector4f transformProject(Vector4f v) {
        return this.matrix.get().transformProject(v);
    }

    @Override
    public Vector4f transformProject(Vector4fc v, Vector4f dest) {
        return this.matrix.get().transformProject(v, dest);
    }

    @Override
    public Vector4f transformProject(float x, float y, float z, float w, Vector4f dest) {
        return this.matrix.get().transformProject(x, y, z, w, dest);
    }

    @Override
    public Vector3f transformProject(Vector4fc v, Vector3f dest) {
        return this.matrix.get().transformProject(v, dest);
    }

    @Override
    public Vector3f transformProject(float x, float y, float z, float w, Vector3f dest) {
        return this.matrix.get().transformProject(x, y, z, w, dest);
    }

    @Override
    public Vector3f transformProject(Vector3f v) {
        return this.matrix.get().transformProject(v);
    }

    @Override
    public Vector3f transformProject(Vector3fc v, Vector3f dest) {
        return this.matrix.get().transformProject(v, dest);
    }

    @Override
    public Vector3f transformProject(float x, float y, float z, Vector3f dest) {
        return this.matrix.get().transformProject(x, y, z, dest);
    }

    @Override
    public Vector3f transformPosition(Vector3f v) {
        return this.matrix.get().transformPosition(v);
    }

    @Override
    public Vector3f transformPosition(Vector3fc v, Vector3f dest) {
        return this.matrix.get().transformPosition(v, dest);
    }

    @Override
    public Vector3f transformPosition(float x, float y, float z, Vector3f dest) {
        return this.matrix.get().transformPosition(x, y, z, dest);
    }

    @Override
    public Vector3f transformDirection(Vector3f v) {
        return this.matrix.get().transformDirection(v);
    }

    @Override
    public Vector3f transformDirection(Vector3fc v, Vector3f dest) {
        return this.matrix.get().transformDirection(v, dest);
    }

    @Override
    public Vector3f transformDirection(float x, float y, float z, Vector3f dest) {
        return this.matrix.get().transformDirection(x, y, z, dest);
    }

    @Override
    public Vector4f transformAffine(Vector4f v) {
        return this.matrix.get().transformAffine(v);
    }

    @Override
    public Vector4f transformAffine(Vector4fc v, Vector4f dest) {
        return this.matrix.get().transformAffine(v, dest);
    }

    @Override
    public Vector4f transformAffine(float x, float y, float z, float w, Vector4f dest) {
        return this.matrix.get().transformAffine(x, y, z, w, dest);
    }

    @Override
    public Matrix4f scale(Vector3fc xyz, Matrix4f dest) {
        return this.matrix.get().scale(xyz, dest);
    }

    @Override
    public Matrix4f scale(Vector3fc xyz) {
        return this.matrix.get().scale(xyz);
    }

    @Override
    public Matrix4f scale(float xyz, Matrix4f dest) {
        return this.matrix.get().scale(xyz, dest);
    }

    @Override
    public Matrix4f scale(float xyz) {
        return this.matrix.get().scale(xyz);
    }

    @Override
    public Matrix4f scaleXY(float x, float y, Matrix4f dest) {
        return this.matrix.get().scaleXY(x, y, dest);
    }

    @Override
    public Matrix4f scaleXY(float x, float y) {
        return this.matrix.get().scaleXY(x, y);
    }

    @Override
    public Matrix4f scale(float x, float y, float z, Matrix4f dest) {
        return this.matrix.get().scale(x, y, z, dest);
    }

    @Override
    public Matrix4f scale(float x, float y, float z) {
        return this.matrix.get().scale(x, y, z);
    }

    @Override
    public Matrix4f scaleAround(float sx, float sy, float sz, float ox, float oy, float oz, Matrix4f dest) {
        return this.matrix.get().scaleAround(sx, sy, sz, ox, oy, oz, dest);
    }

    @Override
    public Matrix4f scaleAround(float sx, float sy, float sz, float ox, float oy, float oz) {
        return this.matrix.get().scaleAround(sx, sy, sz, ox, oy, oz);
    }

    @Override
    public Matrix4f scaleAround(float factor, float ox, float oy, float oz) {
        return this.matrix.get().scaleAround(factor, ox, oy, oz);
    }

    @Override
    public Matrix4f scaleAround(float factor, float ox, float oy, float oz, Matrix4f dest) {
        return this.matrix.get().scaleAround(factor, ox, oy, oz, dest);
    }

    @Override
    public Matrix4f scaleLocal(float x, float y, float z, Matrix4f dest) {
        return this.matrix.get().scaleLocal(x, y, z, dest);
    }

    @Override
    public Matrix4f scaleLocal(float xyz, Matrix4f dest) {
        return this.matrix.get().scaleLocal(xyz, dest);
    }

    @Override
    public Matrix4f scaleLocal(float xyz) {
        return this.matrix.get().scaleLocal(xyz);
    }

    @Override
    public Matrix4f scaleLocal(float x, float y, float z) {
        return this.matrix.get().scaleLocal(x, y, z);
    }

    @Override
    public Matrix4f scaleAroundLocal(float sx, float sy, float sz, float ox, float oy, float oz, Matrix4f dest) {
        return this.matrix.get().scaleAroundLocal(sx, sy, sz, ox, oy, oz, dest);
    }

    @Override
    public Matrix4f scaleAroundLocal(float sx, float sy, float sz, float ox, float oy, float oz) {
        return this.matrix.get().scaleAroundLocal(sx, sy, sz, ox, oy, oz);
    }

    @Override
    public Matrix4f scaleAroundLocal(float factor, float ox, float oy, float oz) {
        return this.matrix.get().scaleAroundLocal(factor, ox, oy, oz);
    }

    @Override
    public Matrix4f scaleAroundLocal(float factor, float ox, float oy, float oz, Matrix4f dest) {
        return this.matrix.get().scaleAroundLocal(factor, ox, oy, oz, dest);
    }

    @Override
    public Matrix4f rotateX(float ang, Matrix4f dest) {
        return this.matrix.get().rotateX(ang, dest);
    }

    @Override
    public Matrix4f rotateX(float ang) {
        return this.matrix.get().rotateX(ang);
    }

    @Override
    public Matrix4f rotateY(float ang, Matrix4f dest) {
        return this.matrix.get().rotateY(ang, dest);
    }

    @Override
    public Matrix4f rotateY(float ang) {
        return this.matrix.get().rotateY(ang);
    }

    @Override
    public Matrix4f rotateZ(float ang, Matrix4f dest) {
        return this.matrix.get().rotateZ(ang, dest);
    }

    @Override
    public Matrix4f rotateZ(float ang) {
        return this.matrix.get().rotateZ(ang);
    }

    @Override
    public Matrix4f rotateTowardsXY(float dirX, float dirY) {
        return this.matrix.get().rotateTowardsXY(dirX, dirY);
    }

    @Override
    public Matrix4f rotateTowardsXY(float dirX, float dirY, Matrix4f dest) {
        return this.matrix.get().rotateTowardsXY(dirX, dirY, dest);
    }

    @Override
    public Matrix4f rotateXYZ(Vector3fc angles) {
        return this.matrix.get().rotateXYZ(angles);
    }

    @Override
    public Matrix4f rotateXYZ(float angleX, float angleY, float angleZ) {
        return this.matrix.get().rotateXYZ(angleX, angleY, angleZ);
    }

    @Override
    public Matrix4f rotateXYZ(float angleX, float angleY, float angleZ, Matrix4f dest) {
        return this.matrix.get().rotateXYZ(angleX, angleY, angleZ, dest);
    }

    @Override
    public Matrix4f rotateAffineXYZ(float angleX, float angleY, float angleZ) {
        return this.matrix.get().rotateAffineXYZ(angleX, angleY, angleZ);
    }

    @Override
    public Matrix4f rotateAffineXYZ(float angleX, float angleY, float angleZ, Matrix4f dest) {
        return this.matrix.get().rotateAffineXYZ(angleX, angleY, angleZ, dest);
    }

    @Override
    public Matrix4f rotateZYX(Vector3f angles) {
        return this.matrix.get().rotateZYX(angles);
    }

    @Override
    public Matrix4f rotateZYX(float angleZ, float angleY, float angleX) {
        return this.matrix.get().rotateZYX(angleZ, angleY, angleX);
    }

    @Override
    public Matrix4f rotateZYX(float angleZ, float angleY, float angleX, Matrix4f dest) {
        return this.matrix.get().rotateZYX(angleZ, angleY, angleX, dest);
    }

    @Override
    public Matrix4f rotateAffineZYX(float angleZ, float angleY, float angleX) {
        return this.matrix.get().rotateAffineZYX(angleZ, angleY, angleX);
    }

    @Override
    public Matrix4f rotateAffineZYX(float angleZ, float angleY, float angleX, Matrix4f dest) {
        return this.matrix.get().rotateAffineZYX(angleZ, angleY, angleX, dest);
    }

    @Override
    public Matrix4f rotateYXZ(Vector3f angles) {
        return this.matrix.get().rotateYXZ(angles);
    }

    @Override
    public Matrix4f rotateYXZ(float angleY, float angleX, float angleZ) {
        return this.matrix.get().rotateYXZ(angleY, angleX, angleZ);
    }

    @Override
    public Matrix4f rotateYXZ(float angleY, float angleX, float angleZ, Matrix4f dest) {
        return this.matrix.get().rotateYXZ(angleY, angleX, angleZ, dest);
    }

    @Override
    public Matrix4f rotateAffineYXZ(float angleY, float angleX, float angleZ) {
        return this.matrix.get().rotateAffineYXZ(angleY, angleX, angleZ);
    }

    @Override
    public Matrix4f rotateAffineYXZ(float angleY, float angleX, float angleZ, Matrix4f dest) {
        return this.matrix.get().rotateAffineYXZ(angleY, angleX, angleZ, dest);
    }

    @Override
    public Matrix4f rotate(float ang, float x, float y, float z, Matrix4f dest) {
        return this.matrix.get().rotate(ang, x, y, z, dest);
    }

    @Override
    public Matrix4f rotate(float ang, float x, float y, float z) {
        return this.matrix.get().rotate(ang, x, y, z);
    }

    @Override
    public Matrix4f rotateTranslation(float ang, float x, float y, float z, Matrix4f dest) {
        return this.matrix.get().rotateTranslation(ang, x, y, z, dest);
    }

    @Override
    public Matrix4f rotateAffine(float ang, float x, float y, float z, Matrix4f dest) {
        return this.matrix.get().rotateAffine(ang, x, y, z, dest);
    }

    @Override
    public Matrix4f rotateAffine(float ang, float x, float y, float z) {
        return this.matrix.get().rotateAffine(ang, x, y, z);
    }

    @Override
    public Matrix4f rotateLocal(float ang, float x, float y, float z, Matrix4f dest) {
        return this.matrix.get().rotateLocal(ang, x, y, z, dest);
    }

    @Override
    public Matrix4f rotateLocal(float ang, float x, float y, float z) {
        return this.matrix.get().rotateLocal(ang, x, y, z);
    }

    @Override
    public Matrix4f rotateLocalX(float ang, Matrix4f dest) {
        return this.matrix.get().rotateLocalX(ang, dest);
    }

    @Override
    public Matrix4f rotateLocalX(float ang) {
        return this.matrix.get().rotateLocalX(ang);
    }

    @Override
    public Matrix4f rotateLocalY(float ang, Matrix4f dest) {
        return this.matrix.get().rotateLocalY(ang, dest);
    }

    @Override
    public Matrix4f rotateLocalY(float ang) {
        return this.matrix.get().rotateLocalY(ang);
    }

    @Override
    public Matrix4f rotateLocalZ(float ang, Matrix4f dest) {
        return this.matrix.get().rotateLocalZ(ang, dest);
    }

    @Override
    public Matrix4f rotateLocalZ(float ang) {
        return this.matrix.get().rotateLocalZ(ang);
    }

    @Override
    public Matrix4f translate(Vector3fc offset) {
        return this.matrix.get().translate(offset);
    }

    @Override
    public Matrix4f translate(Vector3fc offset, Matrix4f dest) {
        return this.matrix.get().translate(offset, dest);
    }

    @Override
    public Matrix4f translate(float x, float y, float z, Matrix4f dest) {
        return this.matrix.get().translate(x, y, z, dest);
    }

    @Override
    public Matrix4f translate(float x, float y, float z) {
        return this.matrix.get().translate(x, y, z);
    }

    @Override
    public Matrix4f translateLocal(Vector3fc offset) {
        return this.matrix.get().translateLocal(offset);
    }

    @Override
    public Matrix4f translateLocal(Vector3fc offset, Matrix4f dest) {
        return this.matrix.get().translateLocal(offset, dest);
    }

    @Override
    public Matrix4f translateLocal(float x, float y, float z, Matrix4f dest) {
        return this.matrix.get().translateLocal(x, y, z, dest);
    }

    @Override
    public Matrix4f translateLocal(float x, float y, float z) {
        return this.matrix.get().translateLocal(x, y, z);
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        this.matrix.get().writeExternal(out);
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException {
        this.matrix.get().readExternal(in);
    }

    @Override
    public Matrix4f ortho(float left, float right, float bottom, float top, float zNear, float zFar, boolean zZeroToOne, Matrix4f dest) {
        return this.matrix.get().ortho(left, right, bottom, top, zNear, zFar, zZeroToOne, dest);
    }

    @Override
    public Matrix4f ortho(float left, float right, float bottom, float top, float zNear, float zFar, Matrix4f dest) {
        return this.matrix.get().ortho(left, right, bottom, top, zNear, zFar, dest);
    }

    @Override
    public Matrix4f ortho(float left, float right, float bottom, float top, float zNear, float zFar, boolean zZeroToOne) {
        return this.matrix.get().ortho(left, right, bottom, top, zNear, zFar, zZeroToOne);
    }

    @Override
    public Matrix4f ortho(float left, float right, float bottom, float top, float zNear, float zFar) {
        return this.matrix.get().ortho(left, right, bottom, top, zNear, zFar);
    }

    @Override
    public Matrix4f orthoLH(float left, float right, float bottom, float top, float zNear, float zFar, boolean zZeroToOne, Matrix4f dest) {
        return this.matrix.get().orthoLH(left, right, bottom, top, zNear, zFar, zZeroToOne, dest);
    }

    @Override
    public Matrix4f orthoLH(float left, float right, float bottom, float top, float zNear, float zFar, Matrix4f dest) {
        return this.matrix.get().orthoLH(left, right, bottom, top, zNear, zFar, dest);
    }

    @Override
    public Matrix4f orthoLH(float left, float right, float bottom, float top, float zNear, float zFar, boolean zZeroToOne) {
        return this.matrix.get().orthoLH(left, right, bottom, top, zNear, zFar, zZeroToOne);
    }

    @Override
    public Matrix4f orthoLH(float left, float right, float bottom, float top, float zNear, float zFar) {
        return this.matrix.get().orthoLH(left, right, bottom, top, zNear, zFar);
    }

    @Override
    public Matrix4f setOrtho(float left, float right, float bottom, float top, float zNear, float zFar, boolean zZeroToOne) {
        return this.matrix.get().setOrtho(left, right, bottom, top, zNear, zFar, zZeroToOne);
    }

    @Override
    public Matrix4f setOrtho(float left, float right, float bottom, float top, float zNear, float zFar) {
        return this.matrix.get().setOrtho(left, right, bottom, top, zNear, zFar);
    }

    @Override
    public Matrix4f setOrthoLH(float left, float right, float bottom, float top, float zNear, float zFar, boolean zZeroToOne) {
        return this.matrix.get().setOrthoLH(left, right, bottom, top, zNear, zFar, zZeroToOne);
    }

    @Override
    public Matrix4f setOrthoLH(float left, float right, float bottom, float top, float zNear, float zFar) {
        return this.matrix.get().setOrthoLH(left, right, bottom, top, zNear, zFar);
    }

    @Override
    public Matrix4f orthoSymmetric(float width, float height, float zNear, float zFar, boolean zZeroToOne, Matrix4f dest) {
        return this.matrix.get().orthoSymmetric(width, height, zNear, zFar, zZeroToOne, dest);
    }

    @Override
    public Matrix4f orthoSymmetric(float width, float height, float zNear, float zFar, Matrix4f dest) {
        return this.matrix.get().orthoSymmetric(width, height, zNear, zFar, dest);
    }

    @Override
    public Matrix4f orthoSymmetric(float width, float height, float zNear, float zFar, boolean zZeroToOne) {
        return this.matrix.get().orthoSymmetric(width, height, zNear, zFar, zZeroToOne);
    }

    @Override
    public Matrix4f orthoSymmetric(float width, float height, float zNear, float zFar) {
        return this.matrix.get().orthoSymmetric(width, height, zNear, zFar);
    }

    @Override
    public Matrix4f orthoSymmetricLH(float width, float height, float zNear, float zFar, boolean zZeroToOne, Matrix4f dest) {
        return this.matrix.get().orthoSymmetricLH(width, height, zNear, zFar, zZeroToOne, dest);
    }

    @Override
    public Matrix4f orthoSymmetricLH(float width, float height, float zNear, float zFar, Matrix4f dest) {
        return this.matrix.get().orthoSymmetricLH(width, height, zNear, zFar, dest);
    }

    @Override
    public Matrix4f orthoSymmetricLH(float width, float height, float zNear, float zFar, boolean zZeroToOne) {
        return this.matrix.get().orthoSymmetricLH(width, height, zNear, zFar, zZeroToOne);
    }

    @Override
    public Matrix4f orthoSymmetricLH(float width, float height, float zNear, float zFar) {
        return this.matrix.get().orthoSymmetricLH(width, height, zNear, zFar);
    }

    @Override
    public Matrix4f setOrthoSymmetric(float width, float height, float zNear, float zFar, boolean zZeroToOne) {
        return this.matrix.get().setOrthoSymmetric(width, height, zNear, zFar, zZeroToOne);
    }

    @Override
    public Matrix4f setOrthoSymmetric(float width, float height, float zNear, float zFar) {
        return this.matrix.get().setOrthoSymmetric(width, height, zNear, zFar);
    }

    @Override
    public Matrix4f setOrthoSymmetricLH(float width, float height, float zNear, float zFar, boolean zZeroToOne) {
        return this.matrix.get().setOrthoSymmetricLH(width, height, zNear, zFar, zZeroToOne);
    }

    @Override
    public Matrix4f setOrthoSymmetricLH(float width, float height, float zNear, float zFar) {
        return this.matrix.get().setOrthoSymmetricLH(width, height, zNear, zFar);
    }

    @Override
    public Matrix4f ortho2D(float left, float right, float bottom, float top, Matrix4f dest) {
        return this.matrix.get().ortho2D(left, right, bottom, top, dest);
    }

    @Override
    public Matrix4f ortho2D(float left, float right, float bottom, float top) {
        return this.matrix.get().ortho2D(left, right, bottom, top);
    }

    @Override
    public Matrix4f ortho2DLH(float left, float right, float bottom, float top, Matrix4f dest) {
        return this.matrix.get().ortho2DLH(left, right, bottom, top, dest);
    }

    @Override
    public Matrix4f ortho2DLH(float left, float right, float bottom, float top) {
        return this.matrix.get().ortho2DLH(left, right, bottom, top);
    }

    @Override
    public Matrix4f setOrtho2D(float left, float right, float bottom, float top) {
        return this.matrix.get().setOrtho2D(left, right, bottom, top);
    }

    @Override
    public Matrix4f setOrtho2DLH(float left, float right, float bottom, float top) {
        return this.matrix.get().setOrtho2DLH(left, right, bottom, top);
    }

    @Override
    public Matrix4f lookAlong(Vector3fc dir, Vector3fc up) {
        return this.matrix.get().lookAlong(dir, up);
    }

    @Override
    public Matrix4f lookAlong(Vector3fc dir, Vector3fc up, Matrix4f dest) {
        return this.matrix.get().lookAlong(dir, up, dest);
    }

    @Override
    public Matrix4f lookAlong(float dirX, float dirY, float dirZ, float upX, float upY, float upZ, Matrix4f dest) {
        return this.matrix.get().lookAlong(dirX, dirY, dirZ, upX, upY, upZ, dest);
    }

    @Override
    public Matrix4f lookAlong(float dirX, float dirY, float dirZ, float upX, float upY, float upZ) {
        return this.matrix.get().lookAlong(dirX, dirY, dirZ, upX, upY, upZ);
    }

    @Override
    public Matrix4f setLookAlong(Vector3fc dir, Vector3fc up) {
        return this.matrix.get().setLookAlong(dir, up);
    }

    @Override
    public Matrix4f setLookAlong(float dirX, float dirY, float dirZ, float upX, float upY, float upZ) {
        return this.matrix.get().setLookAlong(dirX, dirY, dirZ, upX, upY, upZ);
    }

    @Override
    public Matrix4f setLookAt(Vector3fc eye, Vector3fc center, Vector3fc up) {
        return this.matrix.get().setLookAt(eye, center, up);
    }

    @Override
    public Matrix4f setLookAt(float eyeX, float eyeY, float eyeZ, float centerX, float centerY, float centerZ, float upX, float upY, float upZ) {
        return this.matrix.get().setLookAt(eyeX, eyeY, eyeZ, centerX, centerY, centerZ, upX, upY, upZ);
    }

    @Override
    public Matrix4f lookAt(Vector3fc eye, Vector3fc center, Vector3fc up, Matrix4f dest) {
        return this.matrix.get().lookAt(eye, center, up, dest);
    }

    @Override
    public Matrix4f lookAt(Vector3fc eye, Vector3fc center, Vector3fc up) {
        return this.matrix.get().lookAt(eye, center, up);
    }

    @Override
    public Matrix4f lookAt(float eyeX, float eyeY, float eyeZ, float centerX, float centerY, float centerZ, float upX, float upY, float upZ, Matrix4f dest) {
        return this.matrix.get().lookAt(eyeX, eyeY, eyeZ, centerX, centerY, centerZ, upX, upY, upZ, dest);
    }

    @Override
    public Matrix4f lookAtPerspective(float eyeX, float eyeY, float eyeZ, float centerX, float centerY, float centerZ, float upX, float upY, float upZ, Matrix4f dest) {
        return this.matrix.get().lookAtPerspective(eyeX, eyeY, eyeZ, centerX, centerY, centerZ, upX, upY, upZ, dest);
    }

    @Override
    public Matrix4f lookAt(float eyeX, float eyeY, float eyeZ, float centerX, float centerY, float centerZ, float upX, float upY, float upZ) {
        return this.matrix.get().lookAt(eyeX, eyeY, eyeZ, centerX, centerY, centerZ, upX, upY, upZ);
    }

    @Override
    public Matrix4f setLookAtLH(Vector3fc eye, Vector3fc center, Vector3fc up) {
        return this.matrix.get().setLookAtLH(eye, center, up);
    }

    @Override
    public Matrix4f setLookAtLH(float eyeX, float eyeY, float eyeZ, float centerX, float centerY, float centerZ, float upX, float upY, float upZ) {
        return this.matrix.get().setLookAtLH(eyeX, eyeY, eyeZ, centerX, centerY, centerZ, upX, upY, upZ);
    }

    @Override
    public Matrix4f lookAtLH(Vector3fc eye, Vector3fc center, Vector3fc up, Matrix4f dest) {
        return this.matrix.get().lookAtLH(eye, center, up, dest);
    }

    @Override
    public Matrix4f lookAtLH(Vector3fc eye, Vector3fc center, Vector3fc up) {
        return this.matrix.get().lookAtLH(eye, center, up);
    }

    @Override
    public Matrix4f lookAtLH(float eyeX, float eyeY, float eyeZ, float centerX, float centerY, float centerZ, float upX, float upY, float upZ, Matrix4f dest) {
        return this.matrix.get().lookAtLH(eyeX, eyeY, eyeZ, centerX, centerY, centerZ, upX, upY, upZ, dest);
    }

    @Override
    public Matrix4f lookAtLH(float eyeX, float eyeY, float eyeZ, float centerX, float centerY, float centerZ, float upX, float upY, float upZ) {
        return this.matrix.get().lookAtLH(eyeX, eyeY, eyeZ, centerX, centerY, centerZ, upX, upY, upZ);
    }

    @Override
    public Matrix4f lookAtPerspectiveLH(float eyeX, float eyeY, float eyeZ, float centerX, float centerY, float centerZ, float upX, float upY, float upZ, Matrix4f dest) {
        return this.matrix.get().lookAtPerspectiveLH(eyeX, eyeY, eyeZ, centerX, centerY, centerZ, upX, upY, upZ, dest);
    }

    @Override
    public Matrix4f tile(int x, int y, int w, int h) {
        return this.matrix.get().tile(x, y, w, h);
    }

    @Override
    public Matrix4f tile(int x, int y, int w, int h, Matrix4f dest) {
        return this.matrix.get().tile(x, y, w, h, dest);
    }

    @Override
    public Matrix4f perspective(float fovy, float aspect, float zNear, float zFar, boolean zZeroToOne, Matrix4f dest) {
        return this.matrix.get().perspective(fovy, aspect, zNear, zFar, zZeroToOne, dest);
    }

    @Override
    public Matrix4f perspective(float fovy, float aspect, float zNear, float zFar, Matrix4f dest) {
        return this.matrix.get().perspective(fovy, aspect, zNear, zFar, dest);
    }

    @Override
    public Matrix4f perspective(float fovy, float aspect, float zNear, float zFar, boolean zZeroToOne) {
        return this.matrix.get().perspective(fovy, aspect, zNear, zFar, zZeroToOne);
    }

    @Override
    public Matrix4f perspective(float fovy, float aspect, float zNear, float zFar) {
        return this.matrix.get().perspective(fovy, aspect, zNear, zFar);
    }

    @Override
    public Matrix4f perspectiveRect(float width, float height, float zNear, float zFar, boolean zZeroToOne, Matrix4f dest) {
        return this.matrix.get().perspectiveRect(width, height, zNear, zFar, zZeroToOne, dest);
    }

    @Override
    public Matrix4f perspectiveRect(float width, float height, float zNear, float zFar, Matrix4f dest) {
        return this.matrix.get().perspectiveRect(width, height, zNear, zFar, dest);
    }

    @Override
    public Matrix4f perspectiveRect(float width, float height, float zNear, float zFar, boolean zZeroToOne) {
        return this.matrix.get().perspectiveRect(width, height, zNear, zFar, zZeroToOne);
    }

    @Override
    public Matrix4f perspectiveRect(float width, float height, float zNear, float zFar) {
        return this.matrix.get().perspectiveRect(width, height, zNear, zFar);
    }

    @Override
    public Matrix4f perspectiveOffCenter(float fovy, float offAngleX, float offAngleY, float aspect, float zNear, float zFar, boolean zZeroToOne, Matrix4f dest) {
        return this.matrix.get().perspectiveOffCenter(fovy, offAngleX, offAngleY, aspect, zNear, zFar, zZeroToOne, dest);
    }

    @Override
    public Matrix4f perspectiveOffCenter(float fovy, float offAngleX, float offAngleY, float aspect, float zNear, float zFar, Matrix4f dest) {
        return this.matrix.get().perspectiveOffCenter(fovy, offAngleX, offAngleY, aspect, zNear, zFar, dest);
    }

    @Override
    public Matrix4f perspectiveOffCenter(float fovy, float offAngleX, float offAngleY, float aspect, float zNear, float zFar, boolean zZeroToOne) {
        return this.matrix.get().perspectiveOffCenter(fovy, offAngleX, offAngleY, aspect, zNear, zFar, zZeroToOne);
    }

    @Override
    public Matrix4f perspectiveOffCenter(float fovy, float offAngleX, float offAngleY, float aspect, float zNear, float zFar) {
        return this.matrix.get().perspectiveOffCenter(fovy, offAngleX, offAngleY, aspect, zNear, zFar);
    }

    @Override
    public Matrix4f perspectiveOffCenterFov(float angleLeft, float angleRight, float angleDown, float angleUp, float zNear, float zFar, boolean zZeroToOne) {
        return this.matrix.get().perspectiveOffCenterFov(angleLeft, angleRight, angleDown, angleUp, zNear, zFar, zZeroToOne);
    }

    @Override
    public Matrix4f perspectiveOffCenterFov(float angleLeft, float angleRight, float angleDown, float angleUp, float zNear, float zFar, boolean zZeroToOne, Matrix4f dest) {
        return this.matrix.get().perspectiveOffCenterFov(angleLeft, angleRight, angleDown, angleUp, zNear, zFar, zZeroToOne, dest);
    }

    @Override
    public Matrix4f perspectiveOffCenterFov(float angleLeft, float angleRight, float angleDown, float angleUp, float zNear, float zFar) {
        return this.matrix.get().perspectiveOffCenterFov(angleLeft, angleRight, angleDown, angleUp, zNear, zFar);
    }

    @Override
    public Matrix4f perspectiveOffCenterFov(float angleLeft, float angleRight, float angleDown, float angleUp, float zNear, float zFar, Matrix4f dest) {
        return this.matrix.get().perspectiveOffCenterFov(angleLeft, angleRight, angleDown, angleUp, zNear, zFar, dest);
    }

    @Override
    public Matrix4f perspectiveOffCenterFovLH(float angleLeft, float angleRight, float angleDown, float angleUp, float zNear, float zFar, boolean zZeroToOne) {
        return this.matrix.get().perspectiveOffCenterFovLH(angleLeft, angleRight, angleDown, angleUp, zNear, zFar, zZeroToOne);
    }

    @Override
    public Matrix4f perspectiveOffCenterFovLH(float angleLeft, float angleRight, float angleDown, float angleUp, float zNear, float zFar, boolean zZeroToOne, Matrix4f dest) {
        return this.matrix.get().perspectiveOffCenterFovLH(angleLeft, angleRight, angleDown, angleUp, zNear, zFar, zZeroToOne, dest);
    }

    @Override
    public Matrix4f perspectiveOffCenterFovLH(float angleLeft, float angleRight, float angleDown, float angleUp, float zNear, float zFar) {
        return this.matrix.get().perspectiveOffCenterFovLH(angleLeft, angleRight, angleDown, angleUp, zNear, zFar);
    }

    @Override
    public Matrix4f perspectiveOffCenterFovLH(float angleLeft, float angleRight, float angleDown, float angleUp, float zNear, float zFar, Matrix4f dest) {
        return this.matrix.get().perspectiveOffCenterFovLH(angleLeft, angleRight, angleDown, angleUp, zNear, zFar, dest);
    }

    @Override
    public Matrix4f setPerspective(float fovy, float aspect, float zNear, float zFar, boolean zZeroToOne) {
        return this.matrix.get().setPerspective(fovy, aspect, zNear, zFar, zZeroToOne);
    }

    @Override
    public Matrix4f setPerspective(float fovy, float aspect, float zNear, float zFar) {
        return this.matrix.get().setPerspective(fovy, aspect, zNear, zFar);
    }

    @Override
    public Matrix4f setPerspectiveRect(float width, float height, float zNear, float zFar, boolean zZeroToOne) {
        return this.matrix.get().setPerspectiveRect(width, height, zNear, zFar, zZeroToOne);
    }

    @Override
    public Matrix4f setPerspectiveRect(float width, float height, float zNear, float zFar) {
        return this.matrix.get().setPerspectiveRect(width, height, zNear, zFar);
    }

    @Override
    public Matrix4f setPerspectiveOffCenter(float fovy, float offAngleX, float offAngleY, float aspect, float zNear, float zFar) {
        return this.matrix.get().setPerspectiveOffCenter(fovy, offAngleX, offAngleY, aspect, zNear, zFar);
    }

    @Override
    public Matrix4f setPerspectiveOffCenter(float fovy, float offAngleX, float offAngleY, float aspect, float zNear, float zFar, boolean zZeroToOne) {
        return this.matrix.get().setPerspectiveOffCenter(fovy, offAngleX, offAngleY, aspect, zNear, zFar, zZeroToOne);
    }

    @Override
    public Matrix4f setPerspectiveOffCenterFov(float angleLeft, float angleRight, float angleDown, float angleUp, float zNear, float zFar) {
        return this.matrix.get().setPerspectiveOffCenterFov(angleLeft, angleRight, angleDown, angleUp, zNear, zFar);
    }

    @Override
    public Matrix4f setPerspectiveOffCenterFov(float angleLeft, float angleRight, float angleDown, float angleUp, float zNear, float zFar, boolean zZeroToOne) {
        return this.matrix.get().setPerspectiveOffCenterFov(angleLeft, angleRight, angleDown, angleUp, zNear, zFar, zZeroToOne);
    }

    @Override
    public Matrix4f setPerspectiveOffCenterFovLH(float angleLeft, float angleRight, float angleDown, float angleUp, float zNear, float zFar) {
        return this.matrix.get().setPerspectiveOffCenterFovLH(angleLeft, angleRight, angleDown, angleUp, zNear, zFar);
    }

    @Override
    public Matrix4f setPerspectiveOffCenterFovLH(float angleLeft, float angleRight, float angleDown, float angleUp, float zNear, float zFar, boolean zZeroToOne) {
        return this.matrix.get().setPerspectiveOffCenterFovLH(angleLeft, angleRight, angleDown, angleUp, zNear, zFar, zZeroToOne);
    }

    @Override
    public Matrix4f perspectiveLH(float fovy, float aspect, float zNear, float zFar, boolean zZeroToOne, Matrix4f dest) {
        return this.matrix.get().perspectiveLH(fovy, aspect, zNear, zFar, zZeroToOne, dest);
    }

    @Override
    public Matrix4f perspectiveLH(float fovy, float aspect, float zNear, float zFar, boolean zZeroToOne) {
        return this.matrix.get().perspectiveLH(fovy, aspect, zNear, zFar, zZeroToOne);
    }

    @Override
    public Matrix4f perspectiveLH(float fovy, float aspect, float zNear, float zFar, Matrix4f dest) {
        return this.matrix.get().perspectiveLH(fovy, aspect, zNear, zFar, dest);
    }

    @Override
    public Matrix4f perspectiveLH(float fovy, float aspect, float zNear, float zFar) {
        return this.matrix.get().perspectiveLH(fovy, aspect, zNear, zFar);
    }

    @Override
    public Matrix4f setPerspectiveLH(float fovy, float aspect, float zNear, float zFar, boolean zZeroToOne) {
        return this.matrix.get().setPerspectiveLH(fovy, aspect, zNear, zFar, zZeroToOne);
    }

    @Override
    public Matrix4f setPerspectiveLH(float fovy, float aspect, float zNear, float zFar) {
        return this.matrix.get().setPerspectiveLH(fovy, aspect, zNear, zFar);
    }

    @Override
    public Matrix4f frustum(float left, float right, float bottom, float top, float zNear, float zFar, boolean zZeroToOne, Matrix4f dest) {
        return this.matrix.get().frustum(left, right, bottom, top, zNear, zFar, zZeroToOne, dest);
    }

    @Override
    public Matrix4f frustum(float left, float right, float bottom, float top, float zNear, float zFar, Matrix4f dest) {
        return this.matrix.get().frustum(left, right, bottom, top, zNear, zFar, dest);
    }

    @Override
    public Matrix4f frustum(float left, float right, float bottom, float top, float zNear, float zFar, boolean zZeroToOne) {
        return this.matrix.get().frustum(left, right, bottom, top, zNear, zFar, zZeroToOne);
    }

    @Override
    public Matrix4f frustum(float left, float right, float bottom, float top, float zNear, float zFar) {
        return this.matrix.get().frustum(left, right, bottom, top, zNear, zFar);
    }

    @Override
    public Matrix4f setFrustum(float left, float right, float bottom, float top, float zNear, float zFar, boolean zZeroToOne) {
        return this.matrix.get().setFrustum(left, right, bottom, top, zNear, zFar, zZeroToOne);
    }

    @Override
    public Matrix4f setFrustum(float left, float right, float bottom, float top, float zNear, float zFar) {
        return this.matrix.get().setFrustum(left, right, bottom, top, zNear, zFar);
    }

    @Override
    public Matrix4f frustumLH(float left, float right, float bottom, float top, float zNear, float zFar, boolean zZeroToOne, Matrix4f dest) {
        return this.matrix.get().frustumLH(left, right, bottom, top, zNear, zFar, zZeroToOne, dest);
    }

    @Override
    public Matrix4f frustumLH(float left, float right, float bottom, float top, float zNear, float zFar, boolean zZeroToOne) {
        return this.matrix.get().frustumLH(left, right, bottom, top, zNear, zFar, zZeroToOne);
    }

    @Override
    public Matrix4f frustumLH(float left, float right, float bottom, float top, float zNear, float zFar, Matrix4f dest) {
        return this.matrix.get().frustumLH(left, right, bottom, top, zNear, zFar, dest);
    }

    @Override
    public Matrix4f frustumLH(float left, float right, float bottom, float top, float zNear, float zFar) {
        return this.matrix.get().frustumLH(left, right, bottom, top, zNear, zFar);
    }

    @Override
    public Matrix4f setFrustumLH(float left, float right, float bottom, float top, float zNear, float zFar, boolean zZeroToOne) {
        return this.matrix.get().setFrustumLH(left, right, bottom, top, zNear, zFar, zZeroToOne);
    }

    @Override
    public Matrix4f setFrustumLH(float left, float right, float bottom, float top, float zNear, float zFar) {
        return this.matrix.get().setFrustumLH(left, right, bottom, top, zNear, zFar);
    }

    @Override
    public Matrix4f setFromIntrinsic(float alphaX, float alphaY, float gamma, float u0, float v0, int imgWidth, int imgHeight, float near, float far) {
        return this.matrix.get().setFromIntrinsic(alphaX, alphaY, gamma, u0, v0, imgWidth, imgHeight, near, far);
    }

    @Override
    public Matrix4f rotate(Quaternionfc quat, Matrix4f dest) {
        return this.matrix.get().rotate(quat, dest);
    }

    @Override
    public Matrix4f rotate(Quaternionfc quat) {
        return this.matrix.get().rotate(quat);
    }

    @Override
    public Matrix4f rotateAffine(Quaternionfc quat, Matrix4f dest) {
        return this.matrix.get().rotateAffine(quat, dest);
    }

    @Override
    public Matrix4f rotateAffine(Quaternionfc quat) {
        return this.matrix.get().rotateAffine(quat);
    }

    @Override
    public Matrix4f rotateTranslation(Quaternionfc quat, Matrix4f dest) {
        return this.matrix.get().rotateTranslation(quat, dest);
    }

    @Override
    public Matrix4f rotateAround(Quaternionfc quat, float ox, float oy, float oz) {
        return this.matrix.get().rotateAround(quat, ox, oy, oz);
    }

    @Override
    public Matrix4f rotateAroundAffine(Quaternionfc quat, float ox, float oy, float oz, Matrix4f dest) {
        return this.matrix.get().rotateAroundAffine(quat, ox, oy, oz, dest);
    }

    @Override
    public Matrix4f rotateAround(Quaternionfc quat, float ox, float oy, float oz, Matrix4f dest) {
        return this.matrix.get().rotateAround(quat, ox, oy, oz, dest);
    }

    @Override
    public Matrix4f rotationAround(Quaternionfc quat, float ox, float oy, float oz) {
        return this.matrix.get().rotationAround(quat, ox, oy, oz);
    }

    @Override
    public Matrix4f rotateLocal(Quaternionfc quat, Matrix4f dest) {
        return this.matrix.get().rotateLocal(quat, dest);
    }

    @Override
    public Matrix4f rotateLocal(Quaternionfc quat) {
        return this.matrix.get().rotateLocal(quat);
    }

    @Override
    public Matrix4f rotateAroundLocal(Quaternionfc quat, float ox, float oy, float oz, Matrix4f dest) {
        return this.matrix.get().rotateAroundLocal(quat, ox, oy, oz, dest);
    }

    @Override
    public Matrix4f rotateAroundLocal(Quaternionfc quat, float ox, float oy, float oz) {
        return this.matrix.get().rotateAroundLocal(quat, ox, oy, oz);
    }

    @Override
    public Matrix4f rotate(AxisAngle4f axisAngle) {
        return this.matrix.get().rotate(axisAngle);
    }

    @Override
    public Matrix4f rotate(AxisAngle4f axisAngle, Matrix4f dest) {
        return this.matrix.get().rotate(axisAngle, dest);
    }

    @Override
    public Matrix4f rotate(float angle, Vector3fc axis) {
        return this.matrix.get().rotate(angle, axis);
    }

    @Override
    public Matrix4f rotate(float angle, Vector3fc axis, Matrix4f dest) {
        return this.matrix.get().rotate(angle, axis, dest);
    }

    @Override
    public Vector4f unproject(float winX, float winY, float winZ, int[] viewport, Vector4f dest) {
        return this.matrix.get().unproject(winX, winY, winZ, viewport, dest);
    }

    @Override
    public Vector3f unproject(float winX, float winY, float winZ, int[] viewport, Vector3f dest) {
        return this.matrix.get().unproject(winX, winY, winZ, viewport, dest);
    }

    @Override
    public Vector4f unproject(Vector3fc winCoords, int[] viewport, Vector4f dest) {
        return this.matrix.get().unproject(winCoords, viewport, dest);
    }

    @Override
    public Vector3f unproject(Vector3fc winCoords, int[] viewport, Vector3f dest) {
        return this.matrix.get().unproject(winCoords, viewport, dest);
    }

    @Override
    public Matrix4f unprojectRay(float winX, float winY, int[] viewport, Vector3f originDest, Vector3f dirDest) {
        return this.matrix.get().unprojectRay(winX, winY, viewport, originDest, dirDest);
    }

    @Override
    public Matrix4f unprojectRay(Vector2fc winCoords, int[] viewport, Vector3f originDest, Vector3f dirDest) {
        return this.matrix.get().unprojectRay(winCoords, viewport, originDest, dirDest);
    }

    @Override
    public Vector4f unprojectInv(Vector3fc winCoords, int[] viewport, Vector4f dest) {
        return this.matrix.get().unprojectInv(winCoords, viewport, dest);
    }

    @Override
    public Vector4f unprojectInv(float winX, float winY, float winZ, int[] viewport, Vector4f dest) {
        return this.matrix.get().unprojectInv(winX, winY, winZ, viewport, dest);
    }

    @Override
    public Matrix4f unprojectInvRay(Vector2fc winCoords, int[] viewport, Vector3f originDest, Vector3f dirDest) {
        return this.matrix.get().unprojectInvRay(winCoords, viewport, originDest, dirDest);
    }

    @Override
    public Matrix4f unprojectInvRay(float winX, float winY, int[] viewport, Vector3f originDest, Vector3f dirDest) {
        return this.matrix.get().unprojectInvRay(winX, winY, viewport, originDest, dirDest);
    }

    @Override
    public Vector3f unprojectInv(Vector3fc winCoords, int[] viewport, Vector3f dest) {
        return this.matrix.get().unprojectInv(winCoords, viewport, dest);
    }

    @Override
    public Vector3f unprojectInv(float winX, float winY, float winZ, int[] viewport, Vector3f dest) {
        return this.matrix.get().unprojectInv(winX, winY, winZ, viewport, dest);
    }

    @Override
    public Vector4f project(float x, float y, float z, int[] viewport, Vector4f winCoordsDest) {
        return this.matrix.get().project(x, y, z, viewport, winCoordsDest);
    }

    @Override
    public Vector3f project(float x, float y, float z, int[] viewport, Vector3f winCoordsDest) {
        return this.matrix.get().project(x, y, z, viewport, winCoordsDest);
    }

    @Override
    public Vector4f project(Vector3fc position, int[] viewport, Vector4f winCoordsDest) {
        return this.matrix.get().project(position, viewport, winCoordsDest);
    }

    @Override
    public Vector3f project(Vector3fc position, int[] viewport, Vector3f winCoordsDest) {
        return this.matrix.get().project(position, viewport, winCoordsDest);
    }

    @Override
    public Matrix4f reflect(float a, float b, float c, float d, Matrix4f dest) {
        return this.matrix.get().reflect(a, b, c, d, dest);
    }

    @Override
    public Matrix4f reflect(float a, float b, float c, float d) {
        return this.matrix.get().reflect(a, b, c, d);
    }

    @Override
    public Matrix4f reflect(float nx, float ny, float nz, float px, float py, float pz) {
        return this.matrix.get().reflect(nx, ny, nz, px, py, pz);
    }

    @Override
    public Matrix4f reflect(float nx, float ny, float nz, float px, float py, float pz, Matrix4f dest) {
        return this.matrix.get().reflect(nx, ny, nz, px, py, pz, dest);
    }

    @Override
    public Matrix4f reflect(Vector3fc normal, Vector3fc point) {
        return this.matrix.get().reflect(normal, point);
    }

    @Override
    public Matrix4f reflect(Quaternionfc orientation, Vector3fc point) {
        return this.matrix.get().reflect(orientation, point);
    }

    @Override
    public Matrix4f reflect(Quaternionfc orientation, Vector3fc point, Matrix4f dest) {
        return this.matrix.get().reflect(orientation, point, dest);
    }

    @Override
    public Matrix4f reflect(Vector3fc normal, Vector3fc point, Matrix4f dest) {
        return this.matrix.get().reflect(normal, point, dest);
    }

    @Override
    public Matrix4f reflection(float a, float b, float c, float d) {
        return this.matrix.get().reflection(a, b, c, d);
    }

    @Override
    public Matrix4f reflection(float nx, float ny, float nz, float px, float py, float pz) {
        return this.matrix.get().reflection(nx, ny, nz, px, py, pz);
    }

    @Override
    public Matrix4f reflection(Vector3fc normal, Vector3fc point) {
        return this.matrix.get().reflection(normal, point);
    }

    @Override
    public Matrix4f reflection(Quaternionfc orientation, Vector3fc point) {
        return this.matrix.get().reflection(orientation, point);
    }

    @Override
    public Vector4f getRow(int row, Vector4f dest) throws IndexOutOfBoundsException {
        return this.matrix.get().getRow(row, dest);
    }

    @Override
    public Vector3f getRow(int row, Vector3f dest) throws IndexOutOfBoundsException {
        return this.matrix.get().getRow(row, dest);
    }

    @Override
    public Matrix4f setRow(int row, Vector4fc src) throws IndexOutOfBoundsException {
        return this.matrix.get().setRow(row, src);
    }

    @Override
    public Vector4f getColumn(int column, Vector4f dest) throws IndexOutOfBoundsException {
        return this.matrix.get().getColumn(column, dest);
    }

    @Override
    public Vector3f getColumn(int column, Vector3f dest) throws IndexOutOfBoundsException {
        return this.matrix.get().getColumn(column, dest);
    }

    @Override
    public Matrix4f setColumn(int column, Vector4fc src) throws IndexOutOfBoundsException {
        return this.matrix.get().setColumn(column, src);
    }

    @Override
    public float get(int column, int row) {
        return this.matrix.get().get(column, row);
    }

    @Override
    public Matrix4f set(int column, int row, float value) {
        return this.matrix.get().set(column, row, value);
    }

    @Override
    public float getRowColumn(int row, int column) {
        return this.matrix.get().getRowColumn(row, column);
    }

    @Override
    public Matrix4f setRowColumn(int row, int column, float value) {
        return this.matrix.get().setRowColumn(row, column, value);
    }

    @Override
    public Matrix4f normal() {
        return this.matrix.get().normal();
    }

    @Override
    public Matrix4f normal(Matrix4f dest) {
        return this.matrix.get().normal(dest);
    }

    @Override
    public Matrix3f normal(Matrix3f dest) {
        return this.matrix.get().normal(dest);
    }

    @Override
    public Matrix4f cofactor3x3() {
        return this.matrix.get().cofactor3x3();
    }

    @Override
    public Matrix3f cofactor3x3(Matrix3f dest) {
        return this.matrix.get().cofactor3x3(dest);
    }

    @Override
    public Matrix4f cofactor3x3(Matrix4f dest) {
        return this.matrix.get().cofactor3x3(dest);
    }

    @Override
    public Matrix4f normalize3x3() {
        return this.matrix.get().normalize3x3();
    }

    @Override
    public Matrix4f normalize3x3(Matrix4f dest) {
        return this.matrix.get().normalize3x3(dest);
    }

    @Override
    public Matrix3f normalize3x3(Matrix3f dest) {
        return this.matrix.get().normalize3x3(dest);
    }

    @Override
    public Vector4f frustumPlane(int plane, Vector4f dest) {
        return this.matrix.get().frustumPlane(plane, dest);
    }

    @Override
    public Vector3f frustumCorner(int corner, Vector3f point) {
        return this.matrix.get().frustumCorner(corner, point);
    }

    @Override
    public Vector3f perspectiveOrigin(Vector3f origin) {
        return this.matrix.get().perspectiveOrigin(origin);
    }

    @Override
    public Vector3f perspectiveInvOrigin(Vector3f dest) {
        return this.matrix.get().perspectiveInvOrigin(dest);
    }

    @Override
    public float perspectiveFov() {
        return this.matrix.get().perspectiveFov();
    }

    @Override
    public float perspectiveNear() {
        return this.matrix.get().perspectiveNear();
    }

    @Override
    public float perspectiveFar() {
        return this.matrix.get().perspectiveFar();
    }

    @Override
    public Vector3f frustumRayDir(float x, float y, Vector3f dir) {
        return this.matrix.get().frustumRayDir(x, y, dir);
    }

    @Override
    public Vector3f positiveZ(Vector3f dir) {
        return this.matrix.get().positiveZ(dir);
    }

    @Override
    public Vector3f normalizedPositiveZ(Vector3f dir) {
        return this.matrix.get().normalizedPositiveZ(dir);
    }

    @Override
    public Vector3f positiveX(Vector3f dir) {
        return this.matrix.get().positiveX(dir);
    }

    @Override
    public Vector3f normalizedPositiveX(Vector3f dir) {
        return this.matrix.get().normalizedPositiveX(dir);
    }

    @Override
    public Vector3f positiveY(Vector3f dir) {
        return this.matrix.get().positiveY(dir);
    }

    @Override
    public Vector3f normalizedPositiveY(Vector3f dir) {
        return this.matrix.get().normalizedPositiveY(dir);
    }

    @Override
    public Vector3f originAffine(Vector3f origin) {
        return this.matrix.get().originAffine(origin);
    }

    @Override
    public Vector3f origin(Vector3f dest) {
        return this.matrix.get().origin(dest);
    }

    @Override
    public Matrix4f shadow(Vector4f light, float a, float b, float c, float d) {
        return this.matrix.get().shadow(light, a, b, c, d);
    }

    @Override
    public Matrix4f shadow(Vector4f light, float a, float b, float c, float d, Matrix4f dest) {
        return this.matrix.get().shadow(light, a, b, c, d, dest);
    }

    @Override
    public Matrix4f shadow(float lightX, float lightY, float lightZ, float lightW, float a, float b, float c, float d) {
        return this.matrix.get().shadow(lightX, lightY, lightZ, lightW, a, b, c, d);
    }

    @Override
    public Matrix4f shadow(float lightX, float lightY, float lightZ, float lightW, float a, float b, float c, float d, Matrix4f dest) {
        return this.matrix.get().shadow(lightX, lightY, lightZ, lightW, a, b, c, d, dest);
    }

    @Override
    public Matrix4f shadow(Vector4f light, Matrix4fc planeTransform, Matrix4f dest) {
        return this.matrix.get().shadow(light, planeTransform, dest);
    }

    @Override
    public Matrix4f shadow(Vector4f light, Matrix4f planeTransform) {
        return this.matrix.get().shadow(light, planeTransform);
    }

    @Override
    public Matrix4f shadow(float lightX, float lightY, float lightZ, float lightW, Matrix4fc planeTransform, Matrix4f dest) {
        return this.matrix.get().shadow(lightX, lightY, lightZ, lightW, planeTransform, dest);
    }

    @Override
    public Matrix4f shadow(float lightX, float lightY, float lightZ, float lightW, Matrix4f planeTransform) {
        return this.matrix.get().shadow(lightX, lightY, lightZ, lightW, planeTransform);
    }

    @Override
    public Matrix4f billboardCylindrical(Vector3fc objPos, Vector3fc targetPos, Vector3fc up) {
        return this.matrix.get().billboardCylindrical(objPos, targetPos, up);
    }

    @Override
    public Matrix4f billboardSpherical(Vector3fc objPos, Vector3fc targetPos, Vector3fc up) {
        return this.matrix.get().billboardSpherical(objPos, targetPos, up);
    }

    @Override
    public Matrix4f billboardSpherical(Vector3fc objPos, Vector3fc targetPos) {
        return this.matrix.get().billboardSpherical(objPos, targetPos);
    }

    @Override
    public int hashCode() {
        return this.matrix.get().hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return this.matrix.get().equals(obj);
    }

    @Override
    public boolean equals(Matrix4fc m, float delta) {
        return this.matrix.get().equals(m, delta);
    }

    @Override
    public Matrix4f pick(float x, float y, float width, float height, int[] viewport, Matrix4f dest) {
        return this.matrix.get().pick(x, y, width, height, viewport, dest);
    }

    @Override
    public Matrix4f pick(float x, float y, float width, float height, int[] viewport) {
        return this.matrix.get().pick(x, y, width, height, viewport);
    }

    @Override
    public boolean isAffine() {
        return this.matrix.get().isAffine();
    }

    @Override
    public Matrix4f swap(Matrix4f other) {
        return this.matrix.get().swap(other);
    }

    @Override
    public Matrix4f arcball(float radius, float centerX, float centerY, float centerZ, float angleX, float angleY, Matrix4f dest) {
        return this.matrix.get().arcball(radius, centerX, centerY, centerZ, angleX, angleY, dest);
    }

    @Override
    public Matrix4f arcball(float radius, Vector3fc center, float angleX, float angleY, Matrix4f dest) {
        return this.matrix.get().arcball(radius, center, angleX, angleY, dest);
    }

    @Override
    public Matrix4f arcball(float radius, float centerX, float centerY, float centerZ, float angleX, float angleY) {
        return this.matrix.get().arcball(radius, centerX, centerY, centerZ, angleX, angleY);
    }

    @Override
    public Matrix4f arcball(float radius, Vector3fc center, float angleX, float angleY) {
        return this.matrix.get().arcball(radius, center, angleX, angleY);
    }

    @Override
    public Matrix4f frustumAabb(Vector3f min, Vector3f max) {
        return this.matrix.get().frustumAabb(min, max);
    }

    @Override
    public Matrix4f projectedGridRange(Matrix4fc projector, float sLower, float sUpper, Matrix4f dest) {
        return this.matrix.get().projectedGridRange(projector, sLower, sUpper, dest);
    }

    @Override
    public Matrix4f perspectiveFrustumSlice(float near, float far, Matrix4f dest) {
        return this.matrix.get().perspectiveFrustumSlice(near, far, dest);
    }

    @Override
    public Matrix4f orthoCrop(Matrix4fc view, Matrix4f dest) {
        return this.matrix.get().orthoCrop(view, dest);
    }

    @Override
    public Matrix4f trapezoidCrop(float p0x, float p0y, float p1x, float p1y, float p2x, float p2y, float p3x, float p3y) {
        return this.matrix.get().trapezoidCrop(p0x, p0y, p1x, p1y, p2x, p2y, p3x, p3y);
    }

    @Override
    public Matrix4f transformAab(float minX, float minY, float minZ, float maxX, float maxY, float maxZ, Vector3f outMin, Vector3f outMax) {
        return this.matrix.get().transformAab(minX, minY, minZ, maxX, maxY, maxZ, outMin, outMax);
    }

    @Override
    public Matrix4f transformAab(Vector3fc min, Vector3fc max, Vector3f outMin, Vector3f outMax) {
        return this.matrix.get().transformAab(min, max, outMin, outMax);
    }

    @Override
    public Matrix4f lerp(Matrix4fc other, float t) {
        return this.matrix.get().lerp(other, t);
    }

    @Override
    public Matrix4f lerp(Matrix4fc other, float t, Matrix4f dest) {
        return this.matrix.get().lerp(other, t, dest);
    }

    @Override
    public Matrix4f rotateTowards(Vector3fc dir, Vector3fc up, Matrix4f dest) {
        return this.matrix.get().rotateTowards(dir, up, dest);
    }

    @Override
    public Matrix4f rotateTowards(Vector3fc dir, Vector3fc up) {
        return this.matrix.get().rotateTowards(dir, up);
    }

    @Override
    public Matrix4f rotateTowards(float dirX, float dirY, float dirZ, float upX, float upY, float upZ) {
        return this.matrix.get().rotateTowards(dirX, dirY, dirZ, upX, upY, upZ);
    }

    @Override
    public Matrix4f rotateTowards(float dirX, float dirY, float dirZ, float upX, float upY, float upZ, Matrix4f dest) {
        return this.matrix.get().rotateTowards(dirX, dirY, dirZ, upX, upY, upZ, dest);
    }

    @Override
    public Matrix4f rotationTowards(Vector3fc dir, Vector3fc up) {
        return this.matrix.get().rotationTowards(dir, up);
    }

    @Override
    public Matrix4f rotationTowards(float dirX, float dirY, float dirZ, float upX, float upY, float upZ) {
        return this.matrix.get().rotationTowards(dirX, dirY, dirZ, upX, upY, upZ);
    }

    @Override
    public Matrix4f translationRotateTowards(Vector3fc pos, Vector3fc dir, Vector3fc up) {
        return this.matrix.get().translationRotateTowards(pos, dir, up);
    }

    @Override
    public Matrix4f translationRotateTowards(float posX, float posY, float posZ, float dirX, float dirY, float dirZ, float upX, float upY, float upZ) {
        return this.matrix.get().translationRotateTowards(posX, posY, posZ, dirX, dirY, dirZ, upX, upY, upZ);
    }

    @Override
    public Vector3f getEulerAnglesZYX(Vector3f dest) {
        return this.matrix.get().getEulerAnglesZYX(dest);
    }

    @Override
    public Vector3f getEulerAnglesXYZ(Vector3f dest) {
        return this.matrix.get().getEulerAnglesXYZ(dest);
    }

    @Override
    public Matrix4f affineSpan(Vector3f corner, Vector3f xDir, Vector3f yDir, Vector3f zDir) {
        return this.matrix.get().affineSpan(corner, xDir, yDir, zDir);
    }

    @Override
    public boolean testPoint(float x, float y, float z) {
        return this.matrix.get().testPoint(x, y, z);
    }

    @Override
    public boolean testSphere(float x, float y, float z, float r) {
        return this.matrix.get().testSphere(x, y, z, r);
    }

    @Override
    public boolean testAab(float minX, float minY, float minZ, float maxX, float maxY, float maxZ) {
        return this.matrix.get().testAab(minX, minY, minZ, maxX, maxY, maxZ);
    }

    @Override
    public Matrix4f obliqueZ(float a, float b) {
        return this.matrix.get().obliqueZ(a, b);
    }

    @Override
    public Matrix4f obliqueZ(float a, float b, Matrix4f dest) {
        return this.matrix.get().obliqueZ(a, b, dest);
    }

    @Override
    public Matrix4f withLookAtUp(Vector3fc up) {
        return this.matrix.get().withLookAtUp(up);
    }

    @Override
    public Matrix4f withLookAtUp(Vector3fc up, Matrix4f dest) {
        return this.matrix.get().withLookAtUp(up, dest);
    }

    @Override
    public Matrix4f withLookAtUp(float upX, float upY, float upZ) {
        return this.matrix.get().withLookAtUp(upX, upY, upZ);
    }

    @Override
    public Matrix4f withLookAtUp(float upX, float upY, float upZ, Matrix4f dest) {
        return this.matrix.get().withLookAtUp(upX, upY, upZ, dest);
    }

    @Override
    public Matrix4f mapXZY() {
        return this.matrix.get().mapXZY();
    }

    @Override
    public Matrix4f mapXZY(Matrix4f dest) {
        return this.matrix.get().mapXZY(dest);
    }

    @Override
    public Matrix4f mapXZnY() {
        return this.matrix.get().mapXZnY();
    }

    @Override
    public Matrix4f mapXZnY(Matrix4f dest) {
        return this.matrix.get().mapXZnY(dest);
    }

    @Override
    public Matrix4f mapXnYnZ() {
        return this.matrix.get().mapXnYnZ();
    }

    @Override
    public Matrix4f mapXnYnZ(Matrix4f dest) {
        return this.matrix.get().mapXnYnZ(dest);
    }

    @Override
    public Matrix4f mapXnZY() {
        return this.matrix.get().mapXnZY();
    }

    @Override
    public Matrix4f mapXnZY(Matrix4f dest) {
        return this.matrix.get().mapXnZY(dest);
    }

    @Override
    public Matrix4f mapXnZnY() {
        return this.matrix.get().mapXnZnY();
    }

    @Override
    public Matrix4f mapXnZnY(Matrix4f dest) {
        return this.matrix.get().mapXnZnY(dest);
    }

    @Override
    public Matrix4f mapYXZ() {
        return this.matrix.get().mapYXZ();
    }

    @Override
    public Matrix4f mapYXZ(Matrix4f dest) {
        return this.matrix.get().mapYXZ(dest);
    }

    @Override
    public Matrix4f mapYXnZ() {
        return this.matrix.get().mapYXnZ();
    }

    @Override
    public Matrix4f mapYXnZ(Matrix4f dest) {
        return this.matrix.get().mapYXnZ(dest);
    }

    @Override
    public Matrix4f mapYZX() {
        return this.matrix.get().mapYZX();
    }

    @Override
    public Matrix4f mapYZX(Matrix4f dest) {
        return this.matrix.get().mapYZX(dest);
    }

    @Override
    public Matrix4f mapYZnX() {
        return this.matrix.get().mapYZnX();
    }

    @Override
    public Matrix4f mapYZnX(Matrix4f dest) {
        return this.matrix.get().mapYZnX(dest);
    }

    @Override
    public Matrix4f mapYnXZ() {
        return this.matrix.get().mapYnXZ();
    }

    @Override
    public Matrix4f mapYnXZ(Matrix4f dest) {
        return this.matrix.get().mapYnXZ(dest);
    }

    @Override
    public Matrix4f mapYnXnZ() {
        return this.matrix.get().mapYnXnZ();
    }

    @Override
    public Matrix4f mapYnXnZ(Matrix4f dest) {
        return this.matrix.get().mapYnXnZ(dest);
    }

    @Override
    public Matrix4f mapYnZX() {
        return this.matrix.get().mapYnZX();
    }

    @Override
    public Matrix4f mapYnZX(Matrix4f dest) {
        return this.matrix.get().mapYnZX(dest);
    }

    @Override
    public Matrix4f mapYnZnX() {
        return this.matrix.get().mapYnZnX();
    }

    @Override
    public Matrix4f mapYnZnX(Matrix4f dest) {
        return this.matrix.get().mapYnZnX(dest);
    }

    @Override
    public Matrix4f mapZXY() {
        return this.matrix.get().mapZXY();
    }

    @Override
    public Matrix4f mapZXY(Matrix4f dest) {
        return this.matrix.get().mapZXY(dest);
    }

    @Override
    public Matrix4f mapZXnY() {
        return this.matrix.get().mapZXnY();
    }

    @Override
    public Matrix4f mapZXnY(Matrix4f dest) {
        return this.matrix.get().mapZXnY(dest);
    }

    @Override
    public Matrix4f mapZYX() {
        return this.matrix.get().mapZYX();
    }

    @Override
    public Matrix4f mapZYX(Matrix4f dest) {
        return this.matrix.get().mapZYX(dest);
    }

    @Override
    public Matrix4f mapZYnX() {
        return this.matrix.get().mapZYnX();
    }

    @Override
    public Matrix4f mapZYnX(Matrix4f dest) {
        return this.matrix.get().mapZYnX(dest);
    }

    @Override
    public Matrix4f mapZnXY() {
        return this.matrix.get().mapZnXY();
    }

    @Override
    public Matrix4f mapZnXY(Matrix4f dest) {
        return this.matrix.get().mapZnXY(dest);
    }

    @Override
    public Matrix4f mapZnXnY() {
        return this.matrix.get().mapZnXnY();
    }

    @Override
    public Matrix4f mapZnXnY(Matrix4f dest) {
        return this.matrix.get().mapZnXnY(dest);
    }

    @Override
    public Matrix4f mapZnYX() {
        return this.matrix.get().mapZnYX();
    }

    @Override
    public Matrix4f mapZnYX(Matrix4f dest) {
        return this.matrix.get().mapZnYX(dest);
    }

    @Override
    public Matrix4f mapZnYnX() {
        return this.matrix.get().mapZnYnX();
    }

    @Override
    public Matrix4f mapZnYnX(Matrix4f dest) {
        return this.matrix.get().mapZnYnX(dest);
    }

    @Override
    public Matrix4f mapnXYnZ() {
        return this.matrix.get().mapnXYnZ();
    }

    @Override
    public Matrix4f mapnXYnZ(Matrix4f dest) {
        return this.matrix.get().mapnXYnZ(dest);
    }

    @Override
    public Matrix4f mapnXZY() {
        return this.matrix.get().mapnXZY();
    }

    @Override
    public Matrix4f mapnXZY(Matrix4f dest) {
        return this.matrix.get().mapnXZY(dest);
    }

    @Override
    public Matrix4f mapnXZnY() {
        return this.matrix.get().mapnXZnY();
    }

    @Override
    public Matrix4f mapnXZnY(Matrix4f dest) {
        return this.matrix.get().mapnXZnY(dest);
    }

    @Override
    public Matrix4f mapnXnYZ() {
        return this.matrix.get().mapnXnYZ();
    }

    @Override
    public Matrix4f mapnXnYZ(Matrix4f dest) {
        return this.matrix.get().mapnXnYZ(dest);
    }

    @Override
    public Matrix4f mapnXnYnZ() {
        return this.matrix.get().mapnXnYnZ();
    }

    @Override
    public Matrix4f mapnXnYnZ(Matrix4f dest) {
        return this.matrix.get().mapnXnYnZ(dest);
    }

    @Override
    public Matrix4f mapnXnZY() {
        return this.matrix.get().mapnXnZY();
    }

    @Override
    public Matrix4f mapnXnZY(Matrix4f dest) {
        return this.matrix.get().mapnXnZY(dest);
    }

    @Override
    public Matrix4f mapnXnZnY() {
        return this.matrix.get().mapnXnZnY();
    }

    @Override
    public Matrix4f mapnXnZnY(Matrix4f dest) {
        return this.matrix.get().mapnXnZnY(dest);
    }

    @Override
    public Matrix4f mapnYXZ() {
        return this.matrix.get().mapnYXZ();
    }

    @Override
    public Matrix4f mapnYXZ(Matrix4f dest) {
        return this.matrix.get().mapnYXZ(dest);
    }

    @Override
    public Matrix4f mapnYXnZ() {
        return this.matrix.get().mapnYXnZ();
    }

    @Override
    public Matrix4f mapnYXnZ(Matrix4f dest) {
        return this.matrix.get().mapnYXnZ(dest);
    }

    @Override
    public Matrix4f mapnYZX() {
        return this.matrix.get().mapnYZX();
    }

    @Override
    public Matrix4f mapnYZX(Matrix4f dest) {
        return this.matrix.get().mapnYZX(dest);
    }

    @Override
    public Matrix4f mapnYZnX() {
        return this.matrix.get().mapnYZnX();
    }

    @Override
    public Matrix4f mapnYZnX(Matrix4f dest) {
        return this.matrix.get().mapnYZnX(dest);
    }

    @Override
    public Matrix4f mapnYnXZ() {
        return this.matrix.get().mapnYnXZ();
    }

    @Override
    public Matrix4f mapnYnXZ(Matrix4f dest) {
        return this.matrix.get().mapnYnXZ(dest);
    }

    @Override
    public Matrix4f mapnYnXnZ() {
        return this.matrix.get().mapnYnXnZ();
    }

    @Override
    public Matrix4f mapnYnXnZ(Matrix4f dest) {
        return this.matrix.get().mapnYnXnZ(dest);
    }

    @Override
    public Matrix4f mapnYnZX() {
        return this.matrix.get().mapnYnZX();
    }

    @Override
    public Matrix4f mapnYnZX(Matrix4f dest) {
        return this.matrix.get().mapnYnZX(dest);
    }

    @Override
    public Matrix4f mapnYnZnX() {
        return this.matrix.get().mapnYnZnX();
    }

    @Override
    public Matrix4f mapnYnZnX(Matrix4f dest) {
        return this.matrix.get().mapnYnZnX(dest);
    }

    @Override
    public Matrix4f mapnZXY() {
        return this.matrix.get().mapnZXY();
    }

    @Override
    public Matrix4f mapnZXY(Matrix4f dest) {
        return this.matrix.get().mapnZXY(dest);
    }

    @Override
    public Matrix4f mapnZXnY() {
        return this.matrix.get().mapnZXnY();
    }

    @Override
    public Matrix4f mapnZXnY(Matrix4f dest) {
        return this.matrix.get().mapnZXnY(dest);
    }

    @Override
    public Matrix4f mapnZYX() {
        return this.matrix.get().mapnZYX();
    }

    @Override
    public Matrix4f mapnZYX(Matrix4f dest) {
        return this.matrix.get().mapnZYX(dest);
    }

    @Override
    public Matrix4f mapnZYnX() {
        return this.matrix.get().mapnZYnX();
    }

    @Override
    public Matrix4f mapnZYnX(Matrix4f dest) {
        return this.matrix.get().mapnZYnX(dest);
    }

    @Override
    public Matrix4f mapnZnXY() {
        return this.matrix.get().mapnZnXY();
    }

    @Override
    public Matrix4f mapnZnXY(Matrix4f dest) {
        return this.matrix.get().mapnZnXY(dest);
    }

    @Override
    public Matrix4f mapnZnXnY() {
        return this.matrix.get().mapnZnXnY();
    }

    @Override
    public Matrix4f mapnZnXnY(Matrix4f dest) {
        return this.matrix.get().mapnZnXnY(dest);
    }

    @Override
    public Matrix4f mapnZnYX() {
        return this.matrix.get().mapnZnYX();
    }

    @Override
    public Matrix4f mapnZnYX(Matrix4f dest) {
        return this.matrix.get().mapnZnYX(dest);
    }

    @Override
    public Matrix4f mapnZnYnX() {
        return this.matrix.get().mapnZnYnX();
    }

    @Override
    public Matrix4f mapnZnYnX(Matrix4f dest) {
        return this.matrix.get().mapnZnYnX(dest);
    }

    @Override
    public Matrix4f negateX() {
        return this.matrix.get().negateX();
    }

    @Override
    public Matrix4f negateX(Matrix4f dest) {
        return this.matrix.get().negateX(dest);
    }

    @Override
    public Matrix4f negateY() {
        return this.matrix.get().negateY();
    }

    @Override
    public Matrix4f negateY(Matrix4f dest) {
        return this.matrix.get().negateY(dest);
    }

    @Override
    public Matrix4f negateZ() {
        return this.matrix.get().negateZ();
    }

    @Override
    public Matrix4f negateZ(Matrix4f dest) {
        return this.matrix.get().negateZ(dest);
    }

    @Override
    public boolean isFinite() {
        return this.matrix.get().isFinite();
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return this.matrix.get().clone();
    }
}
