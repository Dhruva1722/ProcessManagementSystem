package com.example.processscannerstockmarket.ChannelsActivity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.processscannerstockmarket.R;
import com.github.mikephil.charting.charts.LineChart;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.io.source.ByteArrayOutputStream;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.AreaBreak;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;

import java.io.IOException;
import java.lang.ref.WeakReference;

public class  PdfExportUtil {

    public interface PdfExportListener {
        void onPdfExportProgress(int progress);

        void onPdfExportComplete();
    }


    public static void exportChartToPdf(LineChart lineChart, String fileName, Context context, PdfExportListener listener) {
        new PdfExportTaskForChart(lineChart, fileName, context, listener).execute();
    }

    public static void exportPageToPdf(View rootView, String fileName, Context context, PdfExportListener listener) {
        new PdfExportTask(rootView, fileName, context, listener).execute();
    }
    private static class PdfExportTask extends AsyncTask<Void, Integer, String> {

        private final WeakReference<View> viewReference;
        private final String fileName;
        private final WeakReference<Context> contextReference;
        private final PdfExportListener listener;
//        private final String additionalContent;

        PdfExportTask(View view, String fileName, Context context, PdfExportListener listener) {
            this.viewReference = new WeakReference<>(view);
            this.fileName = fileName;
            this.contextReference = new WeakReference<>(context);
            this.listener = listener;

        }

        @Override
        protected String doInBackground(Void... voids) {
            View rootView = viewReference.get();
            Context context = contextReference.get();

            if (rootView == null || context == null) {
                return null;
            }

            try {
                // Create a bitmap from the view
                Bitmap viewBitmap = getBitmapFromView(rootView);

                // Create a PDF document
                String filePath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/" + fileName + ".pdf";
                try (PdfWriter writer = new PdfWriter(filePath);
                     PdfDocument pdfDocument = new PdfDocument(writer);
                     Document document = new Document(pdfDocument)) {

                    // Convert the bitmap to an iText Image
                    Image viewImage = new Image(ImageDataFactory.create(bitmapToByteArray(viewBitmap)));

                    // Add the Image to the PDF document
                    document.add(viewImage);

                    // Add additional elements to the PDF, e.g., paragraphs, text, etc.
                    addAdditionalDetails(document, rootView);

                } catch (IOException e) {
                    Log.e("PdfExporter", "Error exporting PDF", e);
                    return null;
                }

                return filePath;

            } catch (Exception e) {
                Log.e("PdfExporter", "Error exporting PDF", e);
                return null;
            }
        }

        private void addAdditionalDetails(Document document, View rootView) {
            // Add a new page for additional details
            document.add(new AreaBreak());

            // Access the TextViews containing additional details
            TextView inputTypeValue = rootView.findViewById(R.id.inputTypeValue);
            TextView highPointValue = rootView.findViewById(R.id.highPointValue);
            TextView hysteresisValue = rootView.findViewById(R.id.hysteresisValue);
            TextView offsetValue = rootView.findViewById(R.id.offsetValue);
            TextView lowRangeValue = rootView.findViewById(R.id.lowRangeValue);
            TextView highRangeValue = rootView.findViewById(R.id.highRangeValue);
            TextView correctionFactorValue = rootView.findViewById(R.id.correctionFarctorValue);

            // Add the additional details to the PDF
            document.add(new Paragraph("Input Type: " + inputTypeValue.getText().toString()));
            document.add(new Paragraph("High Setpoint: " + highPointValue.getText().toString()));
            document.add(new Paragraph("Hysteresis: " + hysteresisValue.getText().toString()));
            document.add(new Paragraph("Offset: " + offsetValue.getText().toString()));
            document.add(new Paragraph("Low Range: " + lowRangeValue.getText().toString()));
            document.add(new Paragraph("High Range: " + highRangeValue.getText().toString()));
            document.add(new Paragraph("Correction Factor: " + correctionFactorValue.getText().toString()));
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            if (listener != null) {
                listener.onPdfExportProgress(values[0]);
            }
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            if (result != null) {
                showToast(contextReference.get(), "PDF Downloaded Successfully");
                if (listener != null) {
                    listener.onPdfExportComplete();
                }
            } else {
                showToast(contextReference.get(), "PDF Download Failed");
            }
        }

        private static Bitmap getBitmapFromView(View view) {
            // Create a bitmap with the same size as the view
            Bitmap bitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);

            // Create a Canvas with the bitmap
            Canvas canvas = new Canvas(bitmap);

            // Draw the view onto the Canvas
            view.draw(canvas);

            return bitmap;
        }

        private static byte[] bitmapToByteArray(Bitmap bitmap) {
            // Convert the Bitmap to a byte array
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
            return stream.toByteArray();
        }

        private void showToast(Context context, String message) {
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
        }
    }

    private static class PdfExportTaskForChart extends AsyncTask<Void, Integer, String> {

        private final WeakReference<LineChart> chartReference;
        private final String fileName;
        private final WeakReference<Context> contextReference;
        private final PdfExportListener listener;

        PdfExportTaskForChart(LineChart lineChart, String fileName, Context context, PdfExportListener listener) {
            this.chartReference = new WeakReference<>(lineChart);
            this.fileName = fileName;
            this.contextReference = new WeakReference<>(context);
            this.listener = listener;
        }

        @Override
        protected String doInBackground(Void... voids) {
            LineChart chart = chartReference.get();
            Context context = contextReference.get();

            if (chart == null || context == null) {
                return null;
            }

            try {
                // Create a bitmap from the LineChart
                Bitmap chartBitmap = getBitmapFromView(chart);

                // Create a PDF document
                String filePath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/" + fileName + ".pdf";
                try (PdfWriter writer = new PdfWriter(filePath);
                     PdfDocument pdfDocument = new PdfDocument(writer);
                     Document document = new Document(pdfDocument)) {

                    // Convert the bitmap to an iText Image
                    Image chartImage = new Image(ImageDataFactory.create(bitmapToByteArray(chartBitmap)));

                    // Add the Image to the PDF document
                    document.add(chartImage);

                } catch (IOException e) {
                    Log.e("PdfExporter", "Error exporting PDF", e);
                    return null;
                }

                return filePath;

            } catch (Exception e) {
                Log.e("PdfExporter", "Error exporting PDF", e);
                return null;
            }
        }

        private Bitmap getBitmapFromView(LineChart view) {
            // Create a bitmap with the same size as the view
            Bitmap bitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);

            // Create a Canvas with the bitmap
            Canvas canvas = new Canvas(bitmap);

            // Draw the view onto the Canvas
            view.draw(canvas);

            return bitmap;
        }

        private static byte[] bitmapToByteArray(Bitmap bitmap) {
            // Convert the Bitmap to a byte array
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
            return stream.toByteArray();
        }
    }
}


