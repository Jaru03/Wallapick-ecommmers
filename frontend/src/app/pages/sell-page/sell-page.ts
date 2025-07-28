import { Component } from '@angular/core';
import { Card } from 'primeng/card';
import { InputText, InputTextModule } from 'primeng/inputtext';
import { FileUpload, FileUploadModule, UploadEvent} from 'primeng/fileupload';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-sell-page',
  imports: [Card, InputTextModule, FileUploadModule, FormsModule],
  templateUrl: './sell-page.html',
  styleUrl: './sell-page.css',
})
export class SellPage {
  
}
