import React, { useState } from 'react';
import { Upload, message, Button } from 'antd';
import ImgCrop from 'antd-img-crop';
import axios from 'axios';
import config from '@/config/env.test';
const { BASE_URL } = config;

export default function UploadAvatar() {
  const [fileList, setFileList] = useState([]);
  const [uploading, setUploading] = useState(false);
  const onChange = ({ fileList: newFileList }) => {
    setFileList(newFileList);
  };

  const onPreview = async (file) => {
    let src = file.url;
    if (!src) {
      src = await new Promise((resolve) => {
        const reader = new FileReader();
        reader.readAsDataURL(file.originFileObj);
        reader.onload = () => resolve(reader.result);
      });
    }
    const image = new Image();
    image.src = src;
    const imgWindow = window.open(src);
    imgWindow?.document.write(image.outerHTML);
  };
  const handleUpload = () => {
    if (fileList.length < 1) {
      message.warn('未选择图片');
      return;
    }
    const formData = new FormData();
    fileList.forEach((file: any) => {
      formData.append('image', file.originFileObj);
    });
    uploadFile(formData);
  };

  const uploadFile = async (formData) => {
    setUploading(true);
    try {
      const res = await axios.post(BASE_URL + '/user/upload', formData, {
        headers: { 'content-type': 'multipart/form-data' },
      });
      console.log(`[UploadAvatar] upload file:`, res);
    } catch (e) {
      console.log(`[UploadAvatar] upload file failed:`, e);
      message.error('图片上传失败');
    } finally {
      setUploading(false);
    }
  };
  return (
    <div>
      <ImgCrop rotate>
        <Upload
          action="https://www.mocky.io/v2/5cc8019d300000980a055e76"
          listType="picture-card"
          fileList={fileList}
          onChange={onChange}
          onPreview={onPreview}
          maxCount={1}
          accept="image/*"
        >
          {fileList.length < 1 && '+ Upload'}
        </Upload>
      </ImgCrop>
      <Button type="primary" onClick={handleUpload}>
        上传
      </Button>
    </div>
  );
}
