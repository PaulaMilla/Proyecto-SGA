import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PacientesInfoComponent } from './pacientes-info.component';

describe('PacientesInfoComponent', () => {
  let component: PacientesInfoComponent;
  let fixture: ComponentFixture<PacientesInfoComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [PacientesInfoComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PacientesInfoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
