import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UploadPreciosComponent } from './upload-precios.component';

describe('UploadPreciosComponent', () => {
  let component: UploadPreciosComponent;
  let fixture: ComponentFixture<UploadPreciosComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [UploadPreciosComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(UploadPreciosComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
